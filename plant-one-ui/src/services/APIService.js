import axios from "axios";

const APIService = {
  init(baseURL) {
    axios.defaults.baseURL = baseURL;
  },

  setHeader(key, value) {
    axios.defaults.headers.common[key] = value;
  },

  removeHeader() {
    axios.defaults.headers.common = {};
  },

  get(resource, params, cancelToken = null) {
    if (cancelToken) return axios.get(resource, { params, cancelToken });

    return axios.get(resource, { params });
  },

  post(resource, data, params) {
    return axios.post(resource, data, params);
  },

  put(resource, data) {
    return axios.put(resource, data);
  },

  delete(resource, data) {
    return axios.delete(resource, { data });
  },

  patch(resource, data) {
    return axios.patch(resource, data);
  },

  CancelToken: axios.CancelToken,

  /**
   * Perform a custom Axios request.
   *
   * data is an object containing the following properties:
   *  - method
   *  - url
   *  - data ... request payload
   *  - auth (optional)
   *    - username
   *    - password
   **/
  customRequest(data) {
    return axios(data);
  },
};

export default APIService;
