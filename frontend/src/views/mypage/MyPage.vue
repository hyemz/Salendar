<template>
  <div class="d-flex flex-column justify-center align-center mt-10">
    <h1>마이페이지</h1>
    <v-card class="mx-auto mt-10 pa-5" width="600" outlined>
      <div class="mt-5 d-flex flex-column align-center">
        <v-avatar class="mx-auto profile" size="150">
          <v-img src="https://cdn.pixabay.com/photo/2020/07/12/07/47/bee-5396362_1280.jpg"></v-img>
        </v-avatar>
      </div>

      <v-list-item three-line>
        <v-list-item-content>
          <v-list-item-title class="headline mb-1 text-center">
            {{ fakenickname }} 님 환영합니다
          </v-list-item-title>
          <hr />
        </v-list-item-content>
      </v-list-item>
      <div>
        <v-form class="d-flex flex-column align-center">
          <v-text-field
            disabled
            label="이메일"
            style="width:500px"
            v-model="myEmail"
          ></v-text-field>

          <v-text-field
            label="닉네임"
            style="width:500px"
            v-model="nickname"
            @keypress.enter="login"
          ></v-text-field>
          <div style="width:500px" class="d-flex justify-space-between align-center">
            <v-text-field
              v-if="!changePwd"
              class="mr-4"
              :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show ? 'text' : 'password'"
              label="비밀번호"
              placeholder="비밀번호를 입력해주세요."
              style="width:200px"
              v-model="password"
              @keypress.enter="login"
              @click:append="show = !show"
            ></v-text-field>
            <v-btn
              :disabled="!canChangePwd"
              color="secondary mx-auto"
              @click="changePwd = true"
              v-if="!changePwd"
              >비밀번호 변경</v-btn
            >
          </div>
          <v-text-field
            v-if="changePwd"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="새 비밀번호"
            style="width:500px"
            v-model="newPwd"
            @keypress.enter="login"
            @click:append="show = !show"
            :rules="[!this.error.password || '영문,숫자 포함 8 자리이상이어야 합니다.']"
          ></v-text-field>
          <v-text-field
            v-if="changePwd"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="새 비밀번호 학인"
            style="width:500px"
            v-model="newPwdConfirm"
            @keypress.enter="login"
            @click:append="show = !show"
            :rules="[!this.error.passwordConfirm || this.error.passwordConfirm]"
          ></v-text-field>
        </v-form>
      </div>
      <div class="d-flex flex-row-reverse mt-4">
        <v-btn :disabled="!isSubmit" @click="updateProfile" color="primary">
          수정하기
        </v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios';
import PV from 'password-validator';
import axiosClient from '../../lib/axiosClient';

export default {
  name: 'mypage',
  data() {
    return {
      changePwd: false,
      canChangePwd: false,
      myEmail: '',
      nickname: '',
      fakenickname: '',
      myPwd: '',
      accessPwd: '',
      password: '',
      newPwd: '',
      newPwdConfirm: '',
      passwordSchema: new PV(),
      error: {
        password: false,
        passwordConfirm: false,
      },
      show: false,
      isSubmit: false,
      component: this,
      // rule: {
      //   passowrd: !this.error.password || 'Max 25 characters',
      // },
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

    // 프로필 가져오기
    axiosClient
      .get('/api/user/token/mypage')
      .then((res) => {
        console.log(res);
        this.myEmail = res.data.usrEmail;
        this.nickname = res.data.usrNick;
        this.fakenickname = res.data.usrNick;
        this.myPwd = res.data.usrPwd;
      })
      .catch((err) => {
        console.log('정보를 불러오는 것을 실패했습니다.', err);
      });
  },
  watch: {
    password: function() {
      this.checkPwd();
      this.checkForm();
    },
    newPwd: function() {
      this.checkForm();
    },
    newPwdConfirm: function() {
      this.checkForm();
    },
    changePwd: function() {
      this.checkForm();
    },
  },
  methods: {
    // 프로필 변경하기
    updateProfile() {
      var userForm;
      if (this.changePwd) {
        userForm = {
          usrNick: this.nickname,
          usrPwd: this.newPwdConfirm,
        };
      } else {
        userForm = {
          usrNick: this.nickname,
          usrPwd: this.myPwd,
        };
      }

      // var userForm = {
      //   usrNick: this.nickname,
      //   usrPwd: this.newPwdConfirm,
      // };
      console.log(userForm);
      axiosClient
        .put('/api/user/token/update', userForm)
        .then((res) => {
          alert('유저 정보가 변경되었습니다.');
          console.log('유저 정보가 변경되었습니다.', res);
          this.$router.push('/salelist');
        })
        .catch((err) => {
          console.log('유저 정보 변경이 실패했습니다.', err);
        });
    },
    checkPwd() {
      axiosClient
        .post('/api/user/token/pwdConfirm', this.password)
        .then((res) => {
          console.log(res);
          this.accessPwd = res.data;
          if (this.password.length >= 0 && this.accessPwd == 'OK') {
            this.canChangePwd = true;
            this.myPwd = this.password;
          } else this.canChangePwd = false;
        })
        .catch((err) => {
          console.log('비밀번호 일치여부 확인 불가', err);
        });
    },
    checkForm() {
      if (this.password.length >= 0 && this.accessPwd == 'OK') this.canChangePwd = true;
      else this.canChangePwd = false;

      if (this.newPwd.length >= 0 && !this.passwordSchema.validate(this.newPwd))
        this.error.password = true;
      else this.error.password = false;

      if (this.newPwdConfirm.length >= 0 && this.newPwd !== this.newPwdConfirm)
        this.error.passwordConfirm = '패스워드와 일치하지 않습니다.';
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      if (!this.changePwd) {
        this.isSubmit = this.canChangePwd && this.nickname;
      } else {
        Object.values(this.error).map((v) => {
          if (v || !this.nickname) isSubmit = false;
        });
        this.isSubmit = isSubmit;
      }
    },
    // 유저 프로필 수정시 동작
    changeProfile() {
      alert('회원정보가 수정되었습니다.');
      if (this.changePwd) {
        var changeForm = {
          usrNick: this.nickname,
          usrPwd: this.newPwd,
        };
      } else {
        changeForm = {
          usrNick: this.nickname,
        };
      }

      axios
        .post('', changeForm)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
