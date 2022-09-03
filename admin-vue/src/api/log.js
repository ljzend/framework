import http from '@/utils/request';

export default {
  /**
   * 获取操作日志列表
   * @param params
   * @returns {Promise<AxiosResponse<any>>}
   */
  async getLogList(params){
    return await http.get('/api/log/operationLog/list', params)
  }
}
