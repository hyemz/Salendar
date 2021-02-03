import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);
import axiosClient from '../lib/axiosClient'

export default new Vuex.Store({
    state: {
        isLogin: false,
        following:[],
    },
    getters: {

    },
    mutations: {
        LOGIN(state, trigger) {
            state.isLogin = trigger;
        },
        UPDATEFOLLOWING(state, trigger) {
            if (trigger) {
                axiosClient
                .get("/api/user/token/followings")
                .then((res) => {
                    console.log("히야앗", res)
                    state.following = res.data
                })
                .catch((err) => {
                    console.log('찜 목록을 불러오지 못했습니다.', err);
                });
            } else {
                state.following = []
            }
        }
    },
    actions: {
        login(context, trigger) {
            context.commit('LOGIN', trigger);
        },
        updateFollowing(context, trigger) {
            context.commit('UPDATEFOLLOWING', trigger)
        }
    }
})
