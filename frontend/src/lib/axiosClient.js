import axios from "axios";

const baseURL = "http://localhost:8080";
const headers = {
  "x-auth-token": localStorage.getItem("jwt"),
};
const axiosClient = axios.create({
  baseURL,
  headers,
});

export default axiosClient;
