import axiosClient from "./axiosClient"

const getFollowing =  
    axiosClient
          .get("/api/user/token/followings")
          .then((res) => {
            console.log(res);
            // this.datas = res.data;
          })
          .catch((err) => {
            console.log('찜 목록을 불러오지 못했습니다.', err);
          });

export default getFollowing;