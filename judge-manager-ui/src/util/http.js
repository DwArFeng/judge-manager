import axios from 'axios';
import Vue from 'vue';

// 根据不同环境设置 baseURL。
const API_PREFIX = '/api';
const VERSION_PREFIX = '/v1';
let urlPrefix;
if (process.env.NODE_ENV === 'development') {
  urlPrefix = 'http://localhost:8080/judge_manager_node_war_exploded';
} else if (process.env.NODE_ENV === 'debug') {
  urlPrefix = 'https://www.debug.com';
} else if (process.env.NODE_ENV === 'production') {
  urlPrefix = 'http://localhost:8080/judge-manager';
}
axios.defaults.baseURL = urlPrefix + API_PREFIX + VERSION_PREFIX;

// 请求超时设置。
axios.defaults.timeout = 10000;

// post请求头
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8';

// 请求拦截器
axios.interceptors.request.use(
  (config) => {
    // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
    const loginInfo = Vue.ls.get('loginInfo');
    if (!(!loginInfo && loginInfo !== 0 && loginInfo !== '')) {
      // eslint-disable-next-line no-param-reassign
      config.headers.Authentication = loginInfo.token;
    }
    return config;
  },
  (error) => Promise.error(error),
);

// 响应拦截器
axios.interceptors.response.use(
  (response) => {
    if (response.status === 200) {
      return Promise.resolve(response);
    }
    return Promise.reject(response);
  },
  // 服务器状态码不是200的情况
  () => {
  },
);

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params,
    })
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}

/**
 * remove方法，对应delete请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function del(url, params) {
  return new Promise((resolve, reject) => {
    axios.delete(url, {
      params,
    })
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}

/**
 * patch方法，对应patch请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function patch(url, params) {
  return new Promise((resolve, reject) => {
    axios.patch(url, params)
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}
