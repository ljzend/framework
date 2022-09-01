export default function hasPermission(params) {
  // 标识是否拥有权限
  let flag = false
  // 从session中获取权限字段
  let codeList = JSON.parse(sessionStorage.getItem('codeList'))
  //判断当前权限字段是否与参数传递过来的字段一致
  for (let i = 0; i < codeList.length; i++) {
    if (params === codeList[i]) {
      flag = true
      break
    }
  }
  return flag
}

