import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
//导入auth脚本
import { getToken, setToken, clearStorage, getTokenTime, setTokenTime, removeTokenTime } from '@/utils/auth'
import qs from 'qs'
//导入刷新token的api脚本
import { refreshTokenApi } from '@/api/user'

// 创建一个axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  timeout: 10000 // request timeout
})

/**
 * 刷新token
 */
function refreshTokenInfo() {
  //设置请求参数
  let param = {
    token: getToken()
  }
  return refreshTokenApi(param).then(res => res)
}

//定义变量，标识是否刷新token
let isRefresh = false
// 发送请求之前进行拦截
service.interceptors.request.use(
  config => {
    //获取当前系统时间
    let currentTime = new Date().getTime()//获取token过期时间
    let expireTime = getTokenTime()
    //判断token是否过期
    if (expireTime > 0) {
      //计算时间
      let min = (expireTime - currentTime) / 1000 / 60
      //如果token离过期时间相差10分钟，则刷新token
      if (min < 10) {
        //判断是否刷新
        if (!isRefresh) {
          //标识刷新
          isRefresh = true
          //调用刷新token的方法
          return refreshTokenInfo().then(res => {
            //判断是否成功
            if (res.success) {
              //设置新的token和过期时间
              setToken(res.data.token)
              setTokenTime(res.data.expireTime)
              //将新的token添加到header头部
              config.headers.token = getToken()
            }
            return config
          }).catch(error => {
          }).finally(() => {
            //修改是否刷新token的状态
            isRefresh = false
          })
        }
      }
    }
    // 从store里面获取token，如果token存在，则将token添加到请求的头部headers中
    if (store.getters.token) {
      // 将token添加到请求的头部
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
//清空sessionStorage
    clearStorage()
//清空token过期时间
    removeTokenTime()
// do something with request error
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 3 * 1000
      })

      if (res.code === 600 || res.code === 50012 || res.code === 50014) {
        MessageBox.confirm(' 用户登录信息过期, 请重新登录 ', ' 系统提示', {
            confirmButtonText: '登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('user/resetToken').then(() => {
            //清空sessionStorage
            clearStorage()
            //清空token过期时间
            removeTokenTime()
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    //清空sessionStorage
    clearStorage()
    //清空token过期时间
    removeTokenTime()
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    })
    return Promise.reject(error)
  }
)
//请求方法
const http = {
  post(url, params) {
    return service.post(url, params, {
      transformRequest: [(params) => {
        return JSON.stringify(params)
      }],
      headers: {
        'Content-Type': 'application/json'
      }
    })
  },
  put(url, params) {
    return service.put(url, params, {
      transformRequest: [(params) => {
        return JSON.stringify(params)
      }],
      headers: {
        'Content-Type': 'application/json'
      }
    })
  },
  get(url, params) {
    return service.get(url, {
      params: params,
      paramsSerializer: (params) => {
        return qs.stringify(params)
      }
    })
  },

  getRestApi(url, params) {
    let _params
    if (Object.is(params, null)) {
      _params = ''
    } else {
      _params = '/'
      for (const key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null && params[key] !== '') {
          _params += `${params[key]}/`
        }
      }
      _params = _params.substr(0, _params.length - 1)
    }
    if (_params) {
      return service.get(`${url}${_params}`)
    } else {
      return service.get(url)
    }
  },

  delete(url, params) {
    let _params
    if (Object.is(params, null)) {
      _params = ''
    } else {
      _params = '/'
      for (const key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null && params[key]
          !== '') {
          _params += `${params[key]}/`
        }
      }
      _params = _params.substr(0, _params.length - 1)
    }
    if (_params) {
      return service.delete(`${url}${_params}`).catch(err => {
        message.error(err.msg)
        return Promise.reject(err)
      })
    } else {
      return service.delete(url).catch(err => {
        message.error(err.msg)
        return Promise.reject(err)
      })
    }
  },

  upload(url, params) {
    return service.post(url, params, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },

  login(url, params) {
    return service.post(url, params, {
      transformRequest: [(params) => {
        return qs.stringify(params)
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
  }
}
export default http
