import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);
// import axiosClient from '../lib/axiosClient'
import axios from 'axios'

export default new Vuex.Store({
    state: {
        isLogin: false,
        following: [],
        nick: '',
    },
    getters: {

    },
    mutations: {
        LOGIN(state, trigger) {
            state.isLogin = trigger;
        },
        UPDATEFOLLOWING(state, trigger) {
            if (trigger) {
                const headers = {
                    "x-auth-token": localStorage.getItem("jwt"),
                };
                const baseURL = 'http://i4a301.p.ssafy.io:8080';
                axios
                .create({
                    baseURL,
                    headers,
                })
                .get("/api/user/token/followings")
                .then((res) => {
                    console.log("following list", res)
                    state.following = res.data
                })
                .catch((err) => {
                    console.log('찜 목록을 불러오지 못했습니다.', err);
                });
            } else {
                console.log('following state')
                state.following = []
            }
        }, 
        UPDATENICKNAME(state, trigger) {
            if (trigger) {
                const headers = {
                    "x-auth-token": localStorage.getItem("jwt"),
                };
                const baseURL = 'http://i4a301.p.ssafy.io:8080';
                axios
                .create({
                    baseURL,
                    headers,
                })
                .get("/api/user/token/mypage")
                .then((res) => {
                    console.log("nickname", res)
                    state.nick = res.data.usrNick
                })
                .catch((err) => {
                    console.log('닉네임 업데이트 실패입니다.', err);
                });
            } else {
                console.log('following state')
                state.nick = ''
            }
            }
    },
    actions: {
    login(context, trigger) {
        context.commit('LOGIN', trigger);
    },
    updateFollowing(context, trigger) {
        context.commit('UPDATEFOLLOWING', trigger);
    },
    updateNickname(context, trigger) {
        context.commit('UPDATENICKNAME', trigger);
    },
}
})
