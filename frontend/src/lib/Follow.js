import axiosClient from "./axiosClient";

const Follow = (storeName) => {
  axiosClient
    .post(`/api/user/token/follow/${storeName}`)
    .then((res) => {
      console.log(res);
      // this.datas = res.data;
    })
    .catch((err) => {
      console.log("찜 목록을 불러오지 못했습니다.", err);
    });
};
