<template>
  <v-container class="">
    <v-row no-gutters>

      <v-col cols="12" sm="1"></v-col>

      <v-col
        cols="12"
        sm="10"
      >
          <div>
              <v-card-title>
                <h1 class="pt-2 pr-4">[{{items.boardType}}] {{items.boardTitle}}</h1>
                <v-spacer></v-spacer>
                <v-btn 
                  v-if="items.usrEmail == myEmail"
                  class="mr-1 ml-4"
                  elevation="2"
                  @click="deletepost"
                  color="red lighten-2"
                  >삭제하기</v-btn>
                <v-btn 
                  v-if="items.usrEmail == myEmail"
                  class="mr-1 ml-4"
                  elevation="2"
                  @click="update"
                  color="grey lighten-2"
                  >수정하기</v-btn>
                <v-btn 
                  class=""
                  elevation="2"
                  @click="backtoboard"
                  color="grey lighten-2"
                  >목록</v-btn>
              </v-card-title>
            <v-card class="mt-2">
              <br>
              <v-card-text
              >
              <h1>분류</h1>
              </v-card-text>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3 mb-4"
                  elevation=""
                  outlined
                  max-width="110"
                >{{items.boardType}}</v-card>

              </div>

              <v-card-text
              >
              <h1>내용</h1>
              </v-card-text>

              <div class="pl-4 pr-4 mb-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation=""
                  outlined
                >{{items.boardContents}}</v-card>
              </div>

              <v-card-text
              >
              <h1>사진</h1>
              </v-card-text>

              <div class="pl-4 pr-4 mb-4">
                <v-card
                  max-height="400"
                  max-width="400"
                >
                  <v-img
                    src="@/assets/productSample/product_image_sample.png"
                    max-height="400"
                    max-width="400"
                    contain
                  >
                    <template v-slot:placeholder>
                      <v-row
                        class="fill-height ma-0"
                        align="center"
                        justify="center"
                      >
                        <v-progress-circular
                          indeterminate
                          color="grey lighten-5"
                        ></v-progress-circular>
                      </v-row>
                    </template>
                  </v-img>
                </v-card>
              </div>

              <v-card-text
              >
              <h1>댓글 작성하기</h1>
              </v-card-text>

              <v-text-field
                class="pl-4 pr-4 mb-4"
                label="댓글을 입력해 주세요!"
                :rules="titlerules"
                hide-details="auto"
                outlined
                v-model="comment"
                @keypress.enter="commentcreate"
                ></v-text-field>
              <v-btn
              elevation="2"
              @click="commentcreate"
              color="grey lighten-2"
              >입력</v-btn>

              <v-card-text
              >
              <h1>댓글</h1>
              </v-card-text>

              <div v-for="(comment, idx) in comments" :key='idx'>
                  <div>
                    작성자 : {{ comment.usrEmail }}
                    내용 : {{ comment.commentContents }}
                    작성시간 : {{ comment.createdDate }}
                    <v-btn color="red" @click="deleteComment(comment.id)">삭제</v-btn>

                  </div>


              </div>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3 mt-1 mb-1"
                  elevation=""
                  outlined
                >저는 뾰루지가 가라앉네요 쫀득하고 지금 계절에 좋아요</v-card>
              </div>
              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3 mt-1 mb-1"
                  elevation=""
                  outlined
                >저는 사계절 내내 피지오겔만 써요 오랫동안 촉촉한 유지하는 거 피지오겔 만한게 없어요</v-card>
              </div>
              <div class="pl-4 pr-4 pb-6">
                <v-card
                  class="pl-3 pr-3"
                  elevation=""
                  outlined
                >부모님이 사용하시는데 아주 만족해하세요. 피부톤이 하애지고 기미가 많이 줄어드셨데요</v-card>
              </div>
            </v-card>         
          </div>
      </v-col>

      <v-col cols="12" sm="1"></v-col>
    </v-row>
  </v-container>
</template>

<script>
import axiosClient from '../../lib/axiosClient'
import axios from 'axios'
export default {
  data () {
    return {
      myEmail: '',
      items: [],
      comment: '',
      comments: [],
    };
  },

  created () {
    this.getItems()
    // 프로필 가져오기
    const headers = {
      "x-auth-token": localStorage.getItem("jwt"),
    };
    const baseURL = "http://localhost:8080/";
    axios
      .create({
        baseURL,
        headers,
      })
      // 
      .get('api/user/token/mypage')
      .then((res) => {
        console.log(res);
        this.myEmail = res.data.usrEmail;
      })
      .catch((err) => {
        console.log(err);
      });
    axios
      .create({
        baseURL,
        headers,
      })
      .get(`api/boardList/${this.$route.params.boardNo}/comment`)
      .then((res) => {
        this.comments = res.data
        console.log(res.data);
      })
  },

  methods: {
    getItems () {
      axiosClient
        .get(`http://localhost:8080/api/boardList/${this.$route.params.boardNo}`)
        .then((res) => {
          this.items = res.data
          console.log(res.data)
        })
        .catch((err) => {
          console.log(err);
        })

    },
    backtoboard () {
      this.$router.push({
      path: '/board'
      })
    },
    update () {
      this.$router.push({
      path: `/board/create/${this.$route.params.boardNo}`
      })
    },
    deletepost () {
      // 사용자 인증을 입력된 내용과 함께 보내기 위한 코드
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://localhost:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        // 게시글 삭제 요청 보냄
        .delete(`api/boardList/token/${this.$route.params.boardNo}`)
        .then((res) => {
          console.log(res);
          // 게시글 삭제가 완료되면 게시판으로 넘어감
          this.$router.push('/board');
        })
        .catch((err) => {
          console.log(err);
        });
        alert("게시글이 삭제 되었습니다.");
      }
    },
    commentcreate () {
      var CommentCreateForm = {
        commentContents: this.comment,
      }
      // 사용자 인증을 댓글 내용과 함께 보내기 위한 코드
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://localhost:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .put(`api/boardList/${this.$route.params.boardNo}/comment`, CommentCreateForm)
        .then((res) => {
          console.log(res);
        });
        alert("댓글 작성이 완료되었습니다.")
    },
    commentdelete (idx) {
      const headers = {
        "x-auth-token": localStorage.getItem('jwt'),
      };
      const baseURL = "http://localhost:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .delete(`api/boardList/token/${this.$route.params.boardNo}/comment/${Number(idx)}`)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });

    }
  }
</script>

<style>

</style>