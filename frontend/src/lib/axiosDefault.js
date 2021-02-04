import axios from "axios";

const baseURL = "http://localhost:8080";
const axiosDefault = axios.create({
  baseURL,
});

export default axiosDefault;
