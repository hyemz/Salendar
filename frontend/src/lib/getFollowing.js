import axiosClient from "./axiosClient"

const getFollowing =
  axiosClient
    .get("/api/user/token/followings");

export default getFollowing;