<template>
  <div class="d-flex flex-column justify-center align-center mt-12">
    <v-container class="mt-12">
      <v-row no-gutters>
        <v-col cols="12" sm="1"></v-col>
        <v-col cols="12" sm="10">
          <div class="mb-10">
            <v-card flat class="mt-12" id="calendarmargin">
              <div class="titlefont text-center"><strong>MY</strong> 페이지</div>
            </v-card>
            <v-divider class="mt-12 mb-9"></v-divider></div></v-col></v-row
    ></v-container>
    <v-card class="mx-auto mt-10 pa-12 mb-12" outlined>
      <div class="mt-5 d-flex flex-column align-center mt-10">
        <v-hover v-slot="{ hover }">
          <v-avatar v-if="!hover" class="mx-auto profile mb-10" size="150">
            <v-img :src="myImg"></v-img>
          </v-avatar>
          <v-avatar
            v-else-if="hover"
            class="mx-auto d-flex justify-center mb-10 align-center profile"
            size="150"
            style="position:relative"
          >
            <v-img :src="myImg" style="opacity:0.4"></v-img>
            <v-file-input
              class="mt-0 pt-0 d-flex justify-center align-center"
              :hide-input="true"
              accept="image/*"
              prepend-icon="mdi-plus"
              color="white"
              v-model="file"
              style="position:absolute"
            ></v-file-input>
          </v-avatar>
        </v-hover>
      </div>

      <v-list-item three-line>
        <v-list-item-content>
          <v-list-item-title class="headline mb-1 text-center" id="hd">
            <strong>{{ fakenickname }}</strong> 님 안녕하세요 😎
          </v-list-item-title>
          <hr />
        </v-list-item-content>
      </v-list-item>
      <div>
        <v-form class="d-flex flex-column align-center">
          <v-text-field
            color="sub"
            disabled
            label="이메일"
            style="width:500px"
            prepend-icon="mdi-email"
            v-model="myEmail"
          ></v-text-field>

          <v-text-field
            color="sub"
            label="닉네임"
            style="width:500px"
            prepend-icon="mdi-emoticon-cool-outline"
            v-model="nickname"
            @keypress.enter="login"
          ></v-text-field>
          <div style="width:500px" class="d-flex justify-space-between align-center">
            <v-text-field
              color="sub"
              prepend-icon="mdi-lock-outline"
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
              elevation="0"
              rounded
              :disabled="!canChangePwd"
              color="main mx-auto"
              @click="changePwd = true"
              v-if="!changePwd"
              class="white--text"
              >비밀번호 변경</v-btn
            >
          </div>
          <v-text-field
            prepend-icon="mdi-lock-outline"
            color="sub"
            v-if="changePwd"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="새 비밀번호"
            style="width:500px"
            v-model="newPwd"
            @keypress.enter="login"
            @click:append="show = !show"
            :rules="[!this.error.password || this.error.password]"
          ></v-text-field>
          <v-text-field
            v-if="changePwd"
            color="sub"
            prepend-icon="mdi-lock-check-outline"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="새 비밀번호 확인"
            style="width:500px"
            v-model="newPwdConfirm"
            @keypress.enter="login"
            @click:append="show = !show"
            :rules="[!this.error.passwordConfirm || this.error.passwordConfirm]"
          ></v-text-field>
          <v-checkbox
            v-model="alarm"
            color="sub"
            label="찜한 매장의 세일 정보를 메일로 받겠습니다."
          ></v-checkbox>
        </v-form>
      </div>
      <div class="d-flex flex-row-reverse mt-4">
        <v-btn
          rounded
          :disabled="!isSubmit"
          @click="updateProfile"
          color="sub"
          elevation="0"
          class="white--text"
        >
          수정하기
        </v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
import PV from 'password-validator';
import axios from 'axios';

export default {
  name: 'mypage',
  data() {
    return {
      changePwd: false,
      canChangePwd: false,
      myEmail: '',
      myImg: '',
      nickname: '',
      fakenickname: '',
      accessPwd: '',
      password: '',
      newPwd: '',
      newPwdConfirm: '',
      alarm: false,
      passwordSchema: new PV(),
      error: {
        password: false,
        passwordConfirm: false,
      },
      show: false,
      isSubmit: false,
      component: this,
      file: '',
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
    const headers = {
      'x-auth-token': localStorage.getItem('jwt'),
    };
    const baseURL = 'http://i4a301.p.ssafy.io:8080';
    axios
      .create({
        baseURL,
        headers,
      })
      .get('/api/user/token/mypage')
      .then((res) => {
        console.log(res);
        this.myEmail = res.data.usrEmail;
        this.nickname = res.data.usrNick;
        this.fakenickname = res.data.usrNick;
        this.alarm = res.data.usrAlarm;

        if (res.data.usrImg) {
          this.myImg = 'data:image/png;base64,' + res.data.usrImg;
        } else {
          this.myImg = 'https://cdn.pixabay.com/photo/2020/07/12/07/47/bee-5396362_1280.jpg';
        }
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
    canChangePwd: function() {
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

    file: function() {
      // this.file.type = 'multipart/form-data'
      this.myImg = URL.createObjectURL(this.file);
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
        'Content-Type': 'multipart/form-data',
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      var fd = new FormData();
      fd.append('usrImg', this.file);

      axios
        .create({
          baseURL,
          headers,
        })
        .put('/api/user/token/profileImg', fd)
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
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
          usrAlarm: this.alarm,
          // usrImgUrl: imgUrl,
        };
      } else {
        userForm = {
          usrNick: this.nickname,
          usrPwd: this.password,
          usrAlarm: this.alarm,
          // usrImgUrl: imgUrl
        };
      }
      // 유저 정보 변경하기
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .put('/api/user/token/update', userForm)
        .then((res) => {
          alert('유저 정보가 변경되었습니다.');
          console.log('유저 정보가 변경되었습니다.', res);
          this.$store.dispatch('updateNickname', true);
          this.$router.push('/salelist');
        })
        .catch((err) => {
          console.log('유저 정보 변경이 실패했습니다.', err);
        });
    },
    checkPwd() {
      // 비밀번호 백엔드와 일치하는지
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .post('/api/user/token/pwdConfirm', this.password)
        .then((res) => {
          this.accessPwd = res.data;
          if (this.password.length > 0 && this.accessPwd == 'OK') {
            this.canChangePwd = true;
          } else this.canChangePwd = false;
        })
        .catch((err) => {
          console.log('비밀번호 일치여부 확인 불가', err);
        });
    },
    checkForm() {
      // if (this.password.length >= 0 && this.accessPwd == 'OK') this.canChangePwd = true;
      // else this.canChangePwd = false;

      if (this.newPwd.length >= 0 && !this.passwordSchema.validate(this.newPwd))
        this.error.password = '영문,숫자 포함 8 자리이상이어야 합니다.';
      else this.error.password = false;

      if (this.password == this.newPwd) {
        this.error.password = '기존 비밀번호는 사용하실 수 없습니다';
      }

      if (this.newPwdConfirm.length >= 0 && this.newPwd !== this.newPwdConfirm)
        this.error.passwordConfirm = '패스워드와 일치하지 않습니다.';
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      if (!this.changePwd) {
        if (this.canChangePwd && this.nickname) {
          isSubmit = true;
        } else {
          isSubmit = false;
        }
      } else {
        Object.values(this.error).map((v) => {
          if (v || !this.nickname) {
            isSubmit = false;
          }
        });
      }
      this.isSubmit = isSubmit;
    },
  },
};
</script>

<style>
.v-application--is-ltr .v-input__prepend-outer {
  margin-right: 0px;
  color: white;
  width: 100px;
}

.mdi-plus::before {
  color: white;
  font-size: 80px;
  opacity: 0.9;
}
#hd {
  font-family: 'NanumBarunGothic' !important;
  font-weight: 500;
}
@font-face {
  font-family: 'NanumBarunGothic';
  font-style: normal;
  font-weight: 1000;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot?#iefix')
      format('embedded-opentype'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.woff') format('woff'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.ttf') format('truetype');
}
</style>
