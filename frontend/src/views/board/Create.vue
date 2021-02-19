<template>
  <v-container class="">
    <v-row no-gutters>

      <v-col cols="12" sm="1"></v-col>

      <v-col
        cols="12"
        sm="10"
      >
        <v-card flat height="96px"></v-card>
        <div v-if="`${this.$route.params.boardNo}`=='undefined'">
          <v-card flat>
            <div class="titlefont" style="font-size:48px">새 글 <strong>작성</strong>하기</div>
          </v-card>
          <v-divider class="mt-12 mb-9"></v-divider>

          <br>
          <br>
          <hr>
          <v-card class="pt-3 pl-3 pr-3 pb-3" flat color="grey lighten-4">
          <v-card-text>
            <h2 style="color: grey">분류</h2>
          </v-card-text>

          <v-col
            class="pl-4"
            cols="12"
            sm="4"
            >
            <v-select
            v-if="this.myEmail === 'master@master.com'"
            color="main"
            :items="category"
            label="글의 분류를 선택하세요"
            outlined
            v-model="type"
            background-color="white"
            ></v-select>
            <v-select
            v-else
            color="main"
            :items="category2"
            label="글의 분류를 선택하세요"
            outlined
            v-model="type"
            background-color="white"
            ></v-select>
          </v-col>


          <v-card-text>
            <h2 style="color: grey">제목</h2>
          </v-card-text>
          
          <v-text-field
            color="main"
            class="pl-4 pr-4 mb-4"
            label="제목을 입력해 주십시오"
            :rules="titlerules"
            hide-details="auto"
            outlined
            v-model="title"
            background-color="white"
            ></v-text-field>

          <v-card-text>
            <h2 style="color: grey">내용</h2>
          </v-card-text>

          <v-textarea
            color="main"
            class="pl-4 pr-4 mb-4"
            label="내용을 입력해 주십시오"
            :rules="contentrules"
            hide-details="auto"
            outlined
            height=200
            v-model="content"
            background-color="white"
            ></v-textarea>

          <v-row justify="end">
            <v-btn 
              class="mr-1 ml-4 mb-6 text-color"
              elevation="2"
              @click="create"
              color="main"
              dark
              outlined
              >저장</v-btn>
            <v-btn 
              class="mb-6 mr-8"
              elevation="2"
              @click="board"
              color="sub"
              outlined
              >목록</v-btn>
          </v-row>
          </v-card>
          <hr>
        </div>

        <div v-else>
          <v-card flat>
            <div class="titlefont" style="font-size:48px">글 <strong>수정</strong>하기</div>
          </v-card>
          <v-divider class="mt-12 mb-9"></v-divider>

          <br>
          <br>
          <hr>
          <v-card class="pt-3 pl-3 pr-3 pb-3" flat color="grey lighten-4">
          <v-card-text>
            <h2 style="color: grey">분류</h2>
          </v-card-text>

          <v-col
            class="pl-4"
            cols="12"
            sm="4"
            >
            <v-select
            v-if="this.myEmail === 'master@master.com'"
            color="main"
            :items="category"
            label="글의 분류를 선택하세요"
            outlined
            v-model="itemss.boardType"
            background-color="white"
            ></v-select>
            <v-select
            v-else
            color="main"
            :items="category2"
            label="글의 분류를 선택하세요"
            outlined
            v-model="itemss.boardType"
            background-color="white"
            ></v-select>
          </v-col>

          <v-card-text>
            <h2 style="color: grey">제목</h2>
          </v-card-text>
          
          <v-text-field
            color="main"
            class="pl-4 pr-4 mb-4"
            label="제목을 입력해 주십시오"
            :rules="titlerules"
            hide-details="auto"
            outlined
            v-model="itemss.boardTitle"
            background-color="white"
            ></v-text-field>

          <v-card-text>
            <h2 style="color: grey">내용</h2>
          </v-card-text>

          <v-textarea
            color="main"
            class="pl-4 pr-4 mb-4"
            label="내용을 입력해 주십시오"
            :rules="contentrules"
            hide-details="auto"
            outlined
            height=200
            v-model="itemss.boardContents"
            background-color="white"
            ></v-textarea>

          <v-row justify="end">
            <v-btn 
              class="mr-1 ml-4 mb-6 text-color"
              elevation="2"
              @click="update"
              color="main"
              dark
              outlined
              >수정</v-btn>
            <v-btn 
              class="mb-6 mr-8"
              elevation="2"
              @click="board"
              color="sub"
              outlined
              >목록</v-btn>
          </v-row>
          </v-card>
          <hr>
        </div>
      </v-col>

      <v-col cols="12" sm="1"></v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
export default {
  name: 'BoardCreate',
  data () {
    return {
      // 새 글 작성에서 template에 들어가는 내용 받는 것
      type: '',
      title: '',
      content: '',
      myEmail: '',
      itemss: [],
      // template의 분류에 들어가는 내용
      category: ['자유 게시판', '리뷰 게시판', '세일 제보 게시판', '공지사항'],
      category2: ['자유 게시판', '리뷰 게시판', '세일 제보 게시판'],
      // 제목과 내용의 작성 규칙을 정해주는 내용
      titlerules: [
        value => (value && value.length >= 3) || '최소 3글자 이상 입력해 주세요',
        value => (value && value.length <= 25) || '25자 이상 입력할 수 없습니다',
      ],
      contentrules: [
        value => (value && value.length >= 3) || '최소 3글자 이상 입력해 주세요',
        value => (value && value.length <= 50) || '50자 이상 입력할 수 없습니다',
      ],
    }
  },

  // 페이지가 켜질 때 자동으로 실행되는 코드
  created() {
    // 디테일 페이지의 내용을 불러오기 위한 코드
    axios
      .get(`http://i4a301.p.ssafy.io:8080/api/boardList/${this.$route.params.boardNo}`)
      .then((res) => {
        this.itemss = res.data
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err);
      })
    const headers = {
      "x-auth-token": localStorage.getItem('jwt'),
    };
    const baseURL = "http://i4a301.p.ssafy.io:8080/";
    axios
      .create({
        baseURL,
        headers,
      })
      .get('api/user/token/mypage')
      .then((res) => {
        console.log(res.data.usrEmail);
        this.myEmail = res.data.usrEmail;
      })
      .catch((err) => {
        console.log(err);
      })
  },

  // 게시판으로 돌아가기, 새 글 작성, 글 수정 함수
  methods: {
    // 게시판으로 돌아가기
    board () {
      this.$router.push({
      path: '/board'
      })
    },
    // 글 생성 함수
    create () {

      // if (this.file) {
      //   var fd = new FormData()
      //   fd.append('usrImg', this.file)
      //   console.log(fd)
        
      //   axios.post( , fd{
      //     headers: {
      //       'Content-Type': 'multipart/form-data'
      //     }
      //   })
      //   .then(res => {
      //     console.log(res.data)
      //     var imgUrl = res.data.url
      //   })
      //   .catch( err => {
      //     console.log(err)
      //   })
      // }

      // template에서 입력되 내용을 묶음
      var BoardCreateForm = {
        // 앞의 이름은 백앤드의 이름 && 뒤의 이름은 template에서 설정한 이름
        boardContents: this.content,
        boardTitle: this.title,
        boardType: this.type,
        // imgUrl: imgUrl
      };
      // 사용자 인증을 입력된 내용과 함께 보내기 위한 코드
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      // 제목과 내용의 길이에 따라서 작성이 되고/안되고 설정해주는 것 ((((진행 중))))
      if(this.titlerules && this.contentrules){
        axios
          .create({
            baseURL,
            headers,
          })
          // 게시글 작성을 요청과 작성된 내용의 묶음을 함께 보냄
          .put('api/boardList/token/createboard', BoardCreateForm)
          .then((res) => {
            console.log(res.data);
            // 게시글 작성이 완료되면 해당 게시글의 디테일 페이지로 이동
            this.$router.push(`/board/detail/${res.data.boardNo}`);
          })
          .catch((err) => {
            console.log(err);
          });
          alert("새 글 작성이 완료되었습니다.");
        } else {
          alert("제목과 내용의 길이에 주의해 주세요.")
          // 제목과 내용의 길이 주의 문구를 따로 설정해 주면 좋을 것 같음 ((((진행 중))))
        }
    },
    // 글의 제목과 내용의 글자 수 제한 확인
    // checkForm() {
    //   if (this.title >= 0 && !EV.validate(this.email)) this.error.email = true;
    //   else this.error.email = false;

    //   if (this.password.length >= 0 && !this.passwordSchema.validate(this.password))
    //     this.error.password = true;
    //   else this.error.password = false;

    //   if (this.passwordConfirm.length >= 0 && this.password !== this.passwordConfirm)
    //     this.error.passwordConfirm = '패스워드와 일치하지 않습니다.';
    //   else this.error.passwordConfirm = false;

    //   let isSubmit = true;
    //   Object.values(this.error).map((v) => {
    //     if (v || !this.nickname) isSubmit = false;
    //   });
    //   this.isSubmit = isSubmit;
    // },
    // 글 수정 함수
    update () {
      // template에서 수정된 내용을 묶음
      var BoardModifyForm = {
        // 앞의 이름은 백앤드의 이름 && 뒤의 이름은 template에서 설정한 이름
        boardContents: this.itemss.boardContents,
        boardTitle: this.itemss.boardTitle,
        boardType: this.itemss.boardType,
        // imgUrl: imgUrl

      };
      // 사용자 인증을 수정된 내용과 함께 보내기 위한 코드
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        // 해당 게시글을 수정하는 요청을 수정된 내용의 묶음과 함께 보냄
        .post(`http://i4a301.p.ssafy.io:8080/api/boardList/token/${this.$route.params.boardNo}`, BoardModifyForm)
        .then((res) => {
          console.log(res);
          // 수정을 완료하면 해당 게시글의 디테일 페이지로 이동
          this.$router.push(`/board/detail/${this.$route.params.boardNo}`);
        })
        .catch((err) => {
          console.log(err);
        });
        alert("글 수정이 완료되었습니다.");
      },
    }
  }
</script>

<style>

</style>