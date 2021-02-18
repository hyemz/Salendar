<template>
  <div class="d-flex flex-column justify-center align-center mt-12 mb-12" id="loginmargin">
    <v-img :src="this.src" width="250px"></v-img>
    <div class="d-flex justify-center align-center mt-10">
      <v-form>
        <v-text-field
          color="main text-center"
          class="box"
          label="이메일을 입력해주세요"
          v-model="email"
          rounded
          clearable
          outlined
          @keypress.enter="login"
          prepend-inner-icon="mdi-email"
          :rules="[!this.error.email || '이메일 형식에 맞춰주세요']"
        ></v-text-field>

        <v-text-field
          color="main"
          class="box"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          label="비밀번호를 입력해주세요"
          v-model="password"
          rounded
          outlined
          clearable
          @keypress.enter="login"
          @click:append="show = !show"
          prepend-inner-icon="mdi-lock"
          :rules="[!this.error.password || '영문,숫자 포함 8 자리이상이어야 합니다.']"
        ></v-text-field>
      </v-form>
    </div>
    <v-btn
      id="login"
      depressed
      @click="login"
      :disabled="!isSubmit"
      color="main"
      height="55px"
      rounded
      class="box mt-9"
      >LOGIN
    </v-btn>
    <div>
      <router-link
        class="grey--text text-decoration-none text-darken-1 font-weight-regular d-flex mt-3"
        to="/signup"
        >아직 회원이 아니신가요?</router-link
      >
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import PV from 'password-validator';
import EV from 'email-validator';

export default {
  name: 'Login',
  data() {
    return {
      src: require('@/assets/Intro/shopping-animate.min.svg'),
      email: '',
      password: '',
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
      },
      show: false,
      isSubmit: false,
      component: this,
    };
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
    email: function() {
      this.checkForm();
    },
  },

  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EV.validate(this.email)) this.error.email = true;
      else this.error.email = false;

      if (this.password.length >= 0 && !this.passwordSchema.validate(this.password))
        this.error.password = true;
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    login() {
      var LoginForm = {
        usrEmail: this.email,
        usrPwd: this.password,
      };
      axios
        .post('http://i4a301.p.ssafy.io:8080/api/user/login', LoginForm)
        .then((res) => {
          localStorage.setItem('jwt', res.data.token);
          this.$store.dispatch('login', true);
          this.$store.dispatch('updateFollowing', true);
          this.$router.push('/salelist');
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
<style>
.box {
  width: 450px;
}
#login {
  align-items: center;
  color: white;
  display: flex;
  flex: 1 0 auto;
  justify-content: inherit;
  line-height: normal;
  position: relative;
  transition: inherit;
  transition-property: opacity;
}
#loginmargin.mt-12 {
  margin-top: 150px !important;
}
</style>
