<template>
  <div class="d-flex flex-column justify-center align-center mt-12 mb-12" id="signupmargin">
    <v-img :src="this.src" width="200px"></v-img>
    <div class="d-flex justify-center align-center mt-10">
      <v-form>
        <v-text-field
          label="이메일을 입력해주세요"
          class="box"
          color="main"
          rounded
          clearable
          outlined
          prepend-inner-icon="mdi-email-outline"
          v-model="email"
          @keypress.enter="login"
          :rules="[!this.error.email || '이메일 형식에 맞춰주세요']"
        ></v-text-field>

        <v-text-field
          label="닉네임을 입력해주세요"
          class="box"
          color="main"
          rounded
          outlined
          clearable
          prepend-inner-icon="mdi-emoticon-cool-outline"
          v-model="nickname"
          @keypress.enter="login"
        ></v-text-field>

        <v-text-field
          class="box"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          label="비밀번호를 입력해주세요"
          rounded
          color="main"
          outlined
          clearable
          prepend-inner-icon="mdi-lock-outline"
          v-model="password"
          @keypress.enter="login"
          @click:append="show = !show"
          :rules="[!this.error.password || '영문,숫자 포함 8 자리이상이어야 합니다.']"
        ></v-text-field>

        <v-text-field
          class="box"
          rounded
          color="main"
          outlined
          clearable
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          label="비밀번호를 다시 입력해주세요"
          prepend-inner-icon="mdi-lock-check-outline"
          v-model="passwordConfirm"
          @keypress.enter="login"
          @click:append="show = !show"
          :rules="[!this.error.passwordConfirm || this.error.passwordConfirm]"
        ></v-text-field>
        <v-checkbox
          class="cbox"
          v-model="alarm"
          color="sub"
          label="찜한 매장의 세일 정보를 메일로 받겠습니다."
        ></v-checkbox>
      </v-form>
    </div>
    <v-btn
      id="join"
      :disabled="!isSubmit"
      @click="signup"
      color="main"
      height="55px"
      rounded
      class="box mt-9"
      >JOIN SALENDAR
    </v-btn>
  </div>
</template>

<script>
import axios from 'axios';
import PV from 'password-validator';
import EV from 'email-validator';

export default {
  name: 'Signup',
  data() {
    return {
      src: require('@/assets/Intro/Timeline-bro.svg'),
      email: '',
      nickname: '',
      password: '',
      passwordConfirm: '',
      alarm: false,
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false,
        passwordConfirm: false,
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
    passwordConfirm: function() {
      this.checkForm();
    },
    email: function() {
      this.checkForm();
    },
    nickname: function() {
      this.checkForm();
    },
  },
  // 미완성
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EV.validate(this.email)) this.error.email = true;
      else this.error.email = false;

      if (this.password.length >= 0 && !this.passwordSchema.validate(this.password))
        this.error.password = true;
      else this.error.password = false;

      if (this.passwordConfirm.length >= 0 && this.password !== this.passwordConfirm)
        this.error.passwordConfirm = '패스워드와 일치하지 않습니다.';
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v || !this.nickname) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    signup() {
      alert('회원가입이 되었습니다! \n설렌다에 오신걸 환영합니다~');
      var ResisterForm = {
        usrNick: this.nickname,
        usrEmail: this.email,
        usrPwd: this.password,
        usrAlarm: this.alarm,
      };
      axios
        .post('http://i4a301.p.ssafy.io:8080/api/user/join', ResisterForm)
        .then((res) => {
          console.log(res);
          this.$router.push('/login');
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
  color: 'main';
}
.v-text-field__slot {
  margin-left: 6px;
}
#join {
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
.cbox {
  padding-left: 15px;
}
.v-input--selection-controls {
  margin-top: 0px !important;
  padding-top: 0px !important;
}
#signupmargin.mt-12 {
  margin-top: 150px !important;
}
</style>
