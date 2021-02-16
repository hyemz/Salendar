import axiosClient from "./axiosClient";

const Follow = (storeName) => {
  axiosClient
    .post(`/api/user/token/follow/${storeName}`)
    .then((res) => {
      console.log(res);
      // this.datas = res.data;
    })
    .catch((err) => {
      console.log("팔로우에 실패했습니다.", err);
    });
};
