<template>
  <v-container class="">
    <v-row no-gutters>

      <v-col cols="12" sm="1"></v-col>

      <v-col
        cols="12"
        sm="10"
      >
        <v-card flat height="96px"></v-card>
        <v-card flat>
          <div class="titlefont" style="font-size:48px">{{items.boardType}}</div>
          <v-row
            justify="end"
            class="mt-12 mb-9"
          >
            <v-btn 
              v-if="items.usrNick == myNick"
              class="mr-1 ml-4"
              elevation="0"
              @click="deletepost"
              color="red"
              outlined
              >삭제하기</v-btn>
            <v-btn 
              v-if="items.usrNick == myNick"
              class="mr-1 ml-4"
              elevation="0"
              @click="update"
              color="sub"
              outlined
              >수정하기</v-btn>
            <v-btn 
              class="mr-3"
              elevation="0"
              @click="backtoboard"
              color="main"
              outlined
              >목록</v-btn>
          </v-row>
        </v-card>
        <hr>
        

        <v-card flat>
          <v-card 
            color="grey lighten-4"
            flat
            >
            <br>
            <v-row>
              <v-col cols="12" sm="7">
                <h1 class="pl-7 pt-2">{{items.boardTitle}}</h1>
              </v-col>
              <v-col cols="12" sm="1"></v-col>
              <v-col cols="12" sm="4">
                <div style="font-size:16px">작성자: {{items.usrNick}}</div>
                <div style="font-size:16px">작성일: {{items.createdDate}}</div>
                <div style="font-size:16px">수정일: {{items.modifiedDate}}</div>
                <div style="font-size:16px">조회수: {{items.hit}}</div>
              </v-col>
            </v-row>
            <br>
          </v-card>

          <hr>

          <v-card
            class="pl-7 pr-7"
            height="500px"
            flat
          >
            <br>
            <div style="font-size:20px">{{items.boardContents}}</div>
            <br>
          </v-card>

          <hr>
          <div class="pl-7 pr-7 grey lighten-4">
          <br>
          <br>
          </div>

          <hr>
          <br>
          <br>


          <v-card-text
          >
          <h1>댓글</h1>
          </v-card-text>
          <hr>
          <br>

          <v-text-field
            color="main"
            class="mb-8 pl-4 pr-4"
            label="댓글을 입력해 주세요!"
            append-icon="mdi-keyboard-return"
            hide-details="auto"
            outlined
            v-model="commentinput"
            @keypress.enter="commentcreate"
            ></v-text-field>

          <div v-for="(comment, idx) in comments" :key='idx'>
            <div class="pl-4 pr-4">
                <hr>
              <v-card
                v-if="idx%2==0"
                class="pl-3 pr-3 grey lighten-4"
                flat
              >
                <v-row justify="between" class="mt-3">
                  <p style="font-size:18px" class="mt-2 mb-2 ml-2">{{ comment.usrNick }}</p>
                  <v-spacer></v-spacer>
                  <div
                    v-if="comment.modifiedDate==comment.createdDate"
                    class="mr-2 mt-2">
                    {{ comment.createdDate.slice(0,10) }}
                  </div>
                  <div
                    v-else
                    class="mr-2 mt-2">
                    (수정됨) {{ comment.modifiedDate.slice(0,10) }}
                  </div>

                </v-row>
                
                <v-row class="mt-3">

                  <div class="mt-4 ml-2" style="font-size:17px">{{ comment.commentContents }}</div>
                  <v-spacer></v-spacer>
                  <v-btn 
                    v-if="comment.usrNick == myNick"
                    class="mt-2"
                    justify="end"
                    color="red"
                    @click="commentdelete(comment.commentNo)"
                    text
                    >삭제</v-btn>
                  <v-dialog
                    v-if="comment.usrNick == myNick"
                    v-model="dialog"
                    persistent
                    max-width="600px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        class="mr-2 mt-2"
                        color="sub"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        text
                      >
                        수정
                      </v-btn>
                    </template>
                    <v-card>
                      <v-card-title>
                        <span class="titlefont" style="font-size:24px">댓글 <strong>수정</strong>하기</span>
                        <v-row justify="end">
                          <v-btn
                            color=""
                            text
                            @click="dialog = false"
                          >
                            <v-icon>mdi-close</v-icon>
                            
                          </v-btn>
                        </v-row>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-row>
                            <v-col cols="12">
                              <v-text-field
                                label="댓글 수정"
                                required
                                v-model="commentmodify"
                                @keypress.enter="commentupdate(comment.commentNo), dialog = false"
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="commentupdate(comment.commentNo), dialog = false"
                        >
                          저장
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-row>
                <div>
                </div>
                <br>
              </v-card>

              <v-card
                v-else
                class="pl-3 pr-3"
                flat
              >
                <v-row justify="between" class="mt-3">
                  <p style="font-size:18px" class="mt-2 mb-2 ml-2">{{ comment.usrNick }}</p>
                  <v-spacer></v-spacer>
                  <div
                    v-if="comment.modifiedDate==comment.createdDate"
                    class="mr-2 mt-2">
                    {{ comment.createdDate.slice(0,10) }}
                  </div>
                  <div
                    v-else
                    class="mr-2 mt-2">
                    (수정됨) {{ comment.modifiedDate.slice(0,10) }}
                  </div>

                </v-row>
                
                <v-row class="mt-3">

                  <div class="mt-4 ml-2" style="font-size:17px">{{ comment.commentContents }}</div>
                  <v-spacer></v-spacer>
                  <v-btn 
                    v-if="comment.usrNick == myNick"
                    class="mt-2"
                    justify="end"
                    color="red"
                    @click="commentdelete(comment.commentNo)"
                    text
                    >삭제</v-btn>
                  <v-dialog
                    v-if="comment.usrNick == myNick"
                    v-model="dialog"
                    persistent
                    max-width="600px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        class="mr-2 mt-2"
                        color="sub"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        text
                      >
                        수정
                      </v-btn>
                    </template>
                    <v-card>
                      <v-card-title>
                        <span class="titlefont" style="font-size:24px">댓글 <strong>수정</strong>하기</span>
                        <v-row justify="end">
                          <v-btn
                            color=""
                            text
                            @click="dialog = false"
                          >
                            <v-icon>mdi-close</v-icon>
                            
                          </v-btn>
                        </v-row>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-row>
                            <v-col cols="12">
                              <v-text-field
                                label="댓글 수정"
                                required
                                v-model="commentmodify"
                                @keypress.enter="commentupdate(comment.commentNo), dialog = false"
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="commentupdate(comment.commentNo), dialog = false"
                        >
                          저장
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-row>
                <div>
                </div>
                <br>
              </v-card>

              <hr>
            </div>
          </div> 
        </v-card>
      </v-col>

      <v-col cols="12" sm="1"></v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      dialog: false,
      myNick: '',
      items: [],
      comment: '',
      comments: [],
      commentinput: '',
      commentmodify: '',
    };
  },
  // computed: {
  //   ...mapState(['like']),
  // },
  // watch: {
  //   liked: function () {
  //     this.liked = []
  //     for (const )
  //   },
  // },

  created () {
    // this.$store.dispatch('updateLike', true)
    // this.$emit()
    this.getItems()
    this.getcomments()
    // 프로필 가져오기
    const headers = {
      "x-auth-token": localStorage.getItem("jwt"),
    };
    const baseURL = "http://i4a301.p.ssafy.io:8080/";
    axios
      .create({
        baseURL,
        headers,
      })
      // 
      .get('api/user/token/mypage')
      .then((res) => {
        console.log(res);
        this.myNick = res.data.usrNick;
      })
      .catch((err) => {
        console.log(err);
      })
    axios
    .create({
      baseURL,
      headers,
    })
      .get('api/user/token/followings')
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  },

  methods: {
    // 전체 게시글 불러오기
    // 게시물에 대한 정보를 가져오는 것
    getItems () {
      axios
        .get(`http://i4a301.p.ssafy.io:8080/api/boardList/${this.$route.params.boardNo}`)
        .then((res) => {
          this.items = res.data
          console.log(res.data)
            res.data.createdDate =
              res.data.createdDate.slice(2, 4) +
              '/' +
              res.data.createdDate.slice(5, 7) + 
              '/' +
              res.data.createdDate.slice(8, 10);
            res.data.modifiedDate =
              res.data.modifiedDate.slice(2, 4) +
              '/' +
              res.data.modifiedDate.slice(5, 7) + 
              '/' +
              res.data.modifiedDate.slice(8, 10);
            // console.log(this.contents, i)
        })
        .catch((err) => {
          console.log(err);
        })

    },
    // 전체 댓글 리스트 불러오기
    getcomments () {
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .get(`api/boardList/${this.$route.params.boardNo}/comment`)
        .then((res) => {
          this.comments = res.data
          console.log(res.data);
          for (var i = 0; i < res.data[i].length; i++) {
            res.data[i].createdDate =
              res.data[i].createdDate.slice(2, 4) +
              '/' +
              res.data[i].createdDate.slice(5, 7) + 
              '/' +
              res.data[i].createdDate.slice(8, 10);
            res.data[i].modifiedDate =
              res.data[i].modifiedDate.slice(2, 4) +
              '/' +
              res.data[i].modifiedDate.slice(5, 7) + 
              '/' +
              res.data[i].modifiedDate.slice(8, 10);
            this.comments.push(res.data[i]);
            // console.log(this.contents, i)
          }
        })
    },
    // 전체 게시판 페이지로 돌아가기
    backtoboard () {
      this.$router.push({
      path: '/board'
      })
    },
    // 게시글 업데이트
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
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
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
    },
    commentcreate () {
      var CommentCreateForm = {
        commentContents: this.commentinput,
      }
      
      // 사용자 인증을 댓글 내용과 함께 보내기 위한 코드
      const headers = {
        "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .put(`api/boardList/${this.$route.params.boardNo}/comment`, CommentCreateForm)
        .then((res) => {
          console.log(res);
          this.getcomments()
          // 댓글을 작성을 완료하면 댓글 입력창을 다시 빈칸으로 바꿔주는 것
          this.commentinput = ''
        })
        .catch((err) => {
          console.log(err);
        });
        alert("댓글 작성이 완료되었습니다.")
    },
    commentupdate (commentNo) {
      var CommentModifyForm = {
        commentContents: this.commentmodify,
      }
      const headers = {
        "x-auth-token": localStorage.getItem('jwt'),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .put(`api/boardList/token/${this.$route.params.boardNo}/comment/${commentNo}`, CommentModifyForm)
        .then((res) => {
          console.log(res);
          this.getcomments()
        })
        .catch((err) => {
          console.log(err);
        });
        alert("댓글이 수정되었습니다.")
    },
    commentdelete (commentNo) {
      const headers = {
        "x-auth-token": localStorage.getItem('jwt'),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .delete(`api/boardList/token/${this.$route.params.boardNo}/comment/${commentNo}`)
        .then((res) => {
          console.log(res);
          this.commentmodify = ''
          this.getcomments()
        })
        .catch((err) => {
          console.log(err);
        });
        alert("댓글이 삭제되었습니다.")
    },
    }
  }
</script>

<style>

</style>