<template>
  <div class="d-flex flex-column justify-center align-center">
    <h1>회원가입</h1>
    <div class="d-flex justify-center">
      <v-form>
        <v-text-field 
         label="이메일을 입력해주세요" 
         style="width:300px" 
         v-model="email" 
         @keypress.enter="login"
         :rules="[ !this.error.email || '이메일 형식에 맞춰주세요' ]"
        ></v-text-field>

        <v-text-field label="닉네임을 입력해주세요" style="width:300px" v-model="nickname" @keypress.enter="login"></v-text-field>
        
        <v-text-field 
         :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
         :type="show ? 'text' : 'password'"
         label="비밀번호를 입력해주세요" 
         style="width:300px" 
         v-model="password" 
         @keypress.enter="login"
         @click:append="show = !show"
         :rules="[ !this.error.password || '영문,숫자 포함 8 자리이상이어야 합니다.' ]"
        ></v-text-field>
        
        <v-text-field
         :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
         :type="show ? 'text' : 'password'"
         label="비밀번호를 다시 입력해주세요" 
         style="width:300px" 
         v-model="passwordConfirm" 
         @keypress.enter="login"
         @click:append="show = !show"
         :rules="[ !this.error.passwordConfirm || this.error.passwordConfirm ]"
        ></v-text-field>
      </v-form>
    </div>
        <v-btn 
          elevation="2"
          @click="signup"
          :disabled="!isSubmit"
          color="primary"
        >signup</v-btn>
  </div>

</template>

<script>
import axios from 'axios'
import PV from "password-validator";
import EV from "email-validator";

export default {
  name: 'Signup',
  data () {
    return {
      email: '',
      nickname: '',
      password: '',
      passwordConfirm: '',
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false,
        passwordConfirm: false,
      },
      show: false,
      isSubmit: false,
      component: this,
      // rule: {
      //   passowrd: !this.error.password || 'Max 25 characters',
      // },
    }
  },
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
      this.checkForm();
    },
    email: function() {
      this.checkForm();
    },
    nickname: function() {
      this.checkForm();
    }
  },
  // 미완성
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EV.validate(this.email))
        this.error.email = true;
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = true;
      else this.error.password = false;

      if (this.passwordConfirm.length >= 0 && (this.password !== this.passwordConfirm ))
        this.error.passwordConfirm = "패스워드와 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v || !this.nickname) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    signup () {
      alert("회원가입 이다아아")
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