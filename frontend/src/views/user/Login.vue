<template>
  <div class="d-flex flex-column justify-center align-center">
    <h1>로그인</h1>
    <div class="d-flex justify-center">
      <v-form>
        <v-text-field label="아이디를 입력해주세요" style="width:300px" v-model="username" @keypress.enter="login"></v-text-field>
        <v-text-field label="비밀번호를 입력해주세요" style="width:300px" v-model="password" type="password" @keypress.enter="login"></v-text-field>
      </v-form>
    </div>
        <v-btn 
          elevation="2"
          @click="login"
        >login</v-btn>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data () {
    return {
      username: '',
      password: '',
    }
  },
  // 미완성
  methods: {
    login () {
      alert("로그인이다아아")
      axios.post()
      .then ( res => {
        localStorage.setItem('jwt', res.data.token)
      .catch ( err => {
        console.log(err)
      })
      })
    }
  }
}
</script>

<style>

</style>