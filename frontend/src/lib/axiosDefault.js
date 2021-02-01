import axios from "axios";

const baseURL = "http://localhost:8080";
const headers = {
  "x-auth-token": localStorage.getItem("jwt"),
};
const axiosDefault = axios.create({
  baseURL,
  headers,
});

export default axiosDefault;
