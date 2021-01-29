import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        isLogin: false,
    },
    getters: {
        // getIsLogin(state) {
        //     return state.isLogin;
        // }
    },
    mutations: {
        LOGIN(state, trigger) {
            state.isLogin = trigger;
        } 
    },
    actions: {
        login(context, trigger) {
            context.commit('LOGIN', trigger);
        },
    }
})
