import axios from "axios";

const baseURL = "http://localhost:8080";

const axiosClient = axios.create({
  baseURL,
});

export default axiosClient;
