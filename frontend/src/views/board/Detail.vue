<template>
  <v-container class="d-flex justify-center">
      <v-card
        width="900px"
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
          <br>

          <div class="pl-4 pr-4">
            <hr>
            <v-card
              class="pl-3 pr-3 mb-8"
              elevation="0"
              max-width=""
            >
            
            <div style="font-size:16px">이름: {{items.usrEmail}}</div>
            <div style="font-size:16px">등록일: {{items.createdDate}}</div>
            <div style="font-size:16px">수정일: {{items.modifiedDate}}</div>
            <div style="font-size:16px">조회수: {{items.hit}}</div>
            </v-card>
            <hr>

          </div>

          <div class="pl-4 pr-4 mb-8">
            <v-card
              class="pl-3 pr-3 mb-2"
              elevation="0"
            ><div style="font-size:17px">{{items.boardContents}}</div></v-card>
          </div>

          <div class="pl-4 pr-4 mb-8">
            <v-card
              max-height="400"
              max-width="400"
              class="mb-2"
              elevation="0"
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
            <hr>
          </div>

          <v-card-text
          >
          <h1>댓글 작성하기</h1>
          </v-card-text>

          <v-text-field
            class="pl-4 pr-4 mb-8"
            label="댓글을 입력해 주세요!"
            append-icon="mdi-keyboard-return"
            hide-details="auto"
            outlined
            v-model="commentinput"
            @keypress.enter="commentcreate"
            ></v-text-field>

          <v-card-text
          >
          <h1>댓글</h1>
          </v-card-text>

          <div v-for="(comment, idx) in comments" :key='idx'>
            <div class="pl-4 pr-4">
              <v-card
                class="pl-3 pr-3 mt-1 mb-1 justify-space-between"
                elevation=""
                outlined
              >
                게시글 번호 : {{ comment.commentNo }} |
                작성자 : {{ comment.usrEmail }} |
                내용 : {{ comment.commentContents }} |
                수정일 : {{ comment.modifiedDate }}
                  <v-btn 
                    v-if="comment.usrEmail == myEmail"
                    class="d-flex justify-end"
                    color="red lighten-2"
                    @click="commentdelete(comment.commentNo)"
                    >삭제</v-btn>
                  <v-dialog
                    v-if="comment.usrEmail == myEmail"
                    v-model="dialog"
                    persistent
                    max-width="600px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        class="d-flex justify-end"
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="on"
                      >
                        수정
                      </v-btn>
                    </template>
                    <v-card>
                      <v-card-title>
                        <span class="headline">댓글 수정하기</span>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-row>
                            <v-col cols="12">
                              <v-text-field
                                label="comment"
                                required
                                v-model="commentmodify"
                                @keypress.enter="commentupdate(comment.commentNo), dialog = false"
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                        <small>*indicates required field</small>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="dialog = false"
                        >
                          Close
                        </v-btn>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="commentupdate(comment.commentNo), dialog = false"
                        >
                          Save
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                <div>
                </div>
                
              </v-card>
            </div>
          </div>    
        </div>
      </v-card>

  </v-container>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      dialog: false,
      myEmail: '',
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
        .get(`http://localhost:8080/api/boardList/${this.$route.params.boardNo}`)
        .then((res) => {
          this.items = res.data
          console.log(res.data)
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
      const baseURL = "http://localhost:8080/";
      axios
        .create({
          baseURL,
          headers,
        })
        .get(`api/boardList/${this.$route.params.boardNo}/comment`)
        .then((res) => {
          this.comments = res.data
          console.log(res);
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
    },
    commentcreate () {
      var CommentCreateForm = {
        commentContents: this.commentinput,
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
      const baseURL = "http://localhost:8080/";
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
      const baseURL = "http://localhost:8080/";
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
    // liked (items) {
    //   console.log(items)
    //   if (items.boardNo) {
    //     alert( items.boardTitle + "게시글의 좋아요를 취소했습니다.")
    //     items.followed = false
    //     this.unLike(items.boardNo)
    //     this.liked = false
    //   } else {
    //     alert( item.title + "게시글의 좋아요를 눌렀습니다.")
    //     item.followed = true
    //     this.Like(items.boardNo)
    //   }
    // },
    // Like() {
    //   const headers = {
    //     "x-auth-token": localStorage.getItem("jwt"),
    //   };
    //   const baseURL = "http://localhost:8080";
    //   axios
    //   .create({
    //     baseURL,
    //     headers,
    //   })
    //   .post(`/api/user/token/like/${this.$route.params.boardNo}`)
    //   .then((res) => {
    //     console.log(res);
    //     this.$store.dispatch('updateLike', true)
    //   })
    //   .catch((err) => {
    //     console.log("좋아요에 실패하였습니다.", err);
    //   });
    // },
    // unLike() {
    //   const headers = {
    //     "x-auth-token": localStorage.getItem("jwt"),
    //   };
    //   const baseURL = "http://localhost:8080";
    //   axios
    //   .create({
    //     baseURL,
    //     headers,
    //   })
    //   .post(`/api/user/token/unlike/${this.$route.params.boardNo}`)
    //   .then((res) => {
    //     console.log(res);
    //     this.$store.dispatch('updateLike', true)
    //   })
    //   .catch((err) => {
    //     console.log("좋아요 해제에 실패하였습니다.", err)
    //   });
    // }

    }
  }
</script>

<style>

</style>