<template>
  <v-container class="mt-12">
    <v-row no-gutters>
      <v-col cols="12" sm="1"></v-col>
      <v-col cols="12" sm="10">
        <div class="mb-10">
          <v-card flat class=" mt-12">
            <div class="titlefont"><strong>전체</strong> 게시판</div>
          </v-card>
          <v-divider class="mt-12 mb-9"></v-divider>
        </div>
        <v-row no-gutters>
          <v-col cols="12" sm="1"></v-col>
          <v-col cols="12" sm="10">
            <v-tabs
              fixed-tabs
              background-color="transparent"
              center-active
              class="mt-12 mb-12"
              id="board"
              color="sub"
            >
              <v-tab to="/board" class="ml-1 text-decoration-none font-weight-medium">
                전체 게시판
              </v-tab>
              <v-tab to="/board/free" class="ml-1 text-decoration-none font-weight-medium">
                자유 게시판
              </v-tab>
              <v-tab to="/board/review" class="ml-1 text-decoration-none font-weight-medium">
                리뷰 게시판
              </v-tab>
              <v-tab to="/board/sale" class="ml-1 text-decoration-none font-weight-medium">
                세일 제보 게시판
              </v-tab>
            </v-tabs>
            <div>
              <v-card-subtitle>
                <v-row>
                  <v-col cols="12" sm="4"></v-col>
                  <v-col cols="12" sm="4"></v-col>
                  <v-col cols="12" sm="4">
                    <v-row justify="end" class="mt-2">
                      <v-text-field
                        label="Search"
                        color="sub"
                        clearable
                        outlined
                        rounded
                        prepend-inner-icon="mdi-magnify"
                        append-icon="mdi-keyboard-return"
                        v-model="search"
                        single-line
                        hide-details
                      ></v-text-field>
                    </v-row>
                  </v-col>
                </v-row>
              </v-card-subtitle>

              <v-data-table
                v-if="isLogin"
                calculate-widths
                :headers="headers"
                :items="contents"
                :search="search"
                class="pl-4 pr-4 elevation-1"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                hide-default-footer
                @page-count="pageCount = $event"
                sort-desc="true"
                sort-by="modifiedDate"
                @click:row="rowClick"
                data-table-header-sort-badge-min-width="300px"
              ></v-data-table>
              <v-data-table
                v-if="!isLogin"
                calculate-widths
                :headers="headers"
                :items="contents"
                :search="search"
                class="pl-4 pr-4 elevation-1"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                hide-default-footer
                @page-count="pageCount = $event"
                sort-desc="true"
                sort-by="modifiedDate"
                @click:row="rowClickLoginMassage"
                data-table-header-sort-badge-min-width="300px"
              ></v-data-table>

              <div class="text-center pt-2 d-flex">
                <v-row>
                  <v-col cols="12" sm="2"></v-col>
                  <v-col cols="12" sm="8">
                    <v-pagination
                      v-model="page"
                      :length="pageCount"
                      color="sub"
                      circle
                      class="mt-12"
                    ></v-pagination>
                  </v-col>
                  <v-col cols="12" sm="2">
                    <v-row justify="end">
                      <v-btn
                        v-if="isLogin"
                        tile
                        outlined
                        class="d-flex align-content-end justify-end mr-4 mt-4"
                        color="sub"
                        @click="create"
                      >
                        <v-icon left color="sub">
                          mdi-pencil
                        </v-icon>
                        New
                      </v-btn>
                    </v-row>
                  </v-col>
                </v-row>
              </div>
            </div>
          </v-col>

          <v-col cols="12" sm="2"></v-col>
        </v-row> </v-col
    ></v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';
import axios from 'axios';
export default {
  created() {
    // 공지사항만 가져오기
    this.getnotifications();
    // 공지사항 제외 게시판 글 가져오기
    this.getboards();
    // 오늘 날짜 받아오기
    this.getToday();
  },
  data() {
    return {
      search: '',
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        {
          width: '90',
          text: '게시글 번호',
          align: 'center',
          sortable: false,
          value: 'modifiedDate',
        },
        // 내용에 sortable: false를 추가하면 제목을 클릭 시 sort 되는 것을 제거
        { width: '60', text: '분류', value: 'boardType', align: 'start', sortable: false },
        { text: '제목', value: 'boardTitle', align: 'start', sortable: false },
        { width: '150', text: '작성자', value: 'usrNick', align: 'start', sortable: false },
        { width: '120', text: '등록일', value: 'createdDate', align: 'center', sortable: false },
        { width: '90', text: '조회수', value: 'hit', align: 'center', sortable: false },
      ],
      contents: [],
    };
  },
  computed: {
    ...mapState(['isLogin']),
  },
  watch: {
    isLogin: function() {
      this.token = localStorage.getItem('jwt');
      if (this.token) {
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
            this.nickname = res.data.usrNick;
            console.log(this.nickname);
            this.$store.dispatch('login', true);
          })
          .catch((err) => {
            console.log(err);
            localStorage.removeItem('jwt');
            this.$store.dispatch('login', false);
          });
      }
    },
  },
  methods: {
    getToday() {
      var date = new Date();
      var year = date.getFullYear();
      var month = ('0' + (1 + date.getMonth())).slice(-2);
      var day = ('0' + date.getDate()).slice(-2);

      return year + '-' + month + '-' + day;
    },
    getnotifications() {
      axios
        .get('http://i4a301.p.ssafy.io:8080/api/boardList/')
        .then((res) => {
          console.log(
            res.data[1].createdDate.slice(undefined, 4) +
              '/' +
              res.data[1].createdDate.slice(5, 7) +
              '/' +
              res.data[1].createdDate.slice(8, 10)
          );
          console.log(res.data[1].createdDate.slice(undefined, 10));
          for (var i = 0; i < res.data.length; i++) {
            if (res.data[i].boardType === '공지사항') {
              res.data[i].createdDate =
                res.data[i].createdDate.slice(2, 4) +
                '/' +
                res.data[i].createdDate.slice(5, 7) + 
                '/' +
                res.data[i].createdDate.slice(8, 10);
              res.data[i].modifiedDate = res.data[i].boardType;
              res.data[i].boardType = '공지';
              this.contents.push(res.data[i]);
              // console.log(this.contents, i)
            }
          }
          // console.log(this.contents)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getboards() {
      axios
        .get('http://i4a301.p.ssafy.io:8080/api/boardList/')
        .then((res) => {
          for (var i = 0; i < res.data.length; i++) {
            if (res.data[i].boardType === '리뷰 게시판') {
              res.data[i].createdDate =
                res.data[i].createdDate.slice(2, 4) +
                '/' +
                res.data[i].createdDate.slice(5, 7) + 
                '/' +
                res.data[i].createdDate.slice(8, 10);
              res.data[i].modifiedDate = res.data[i].boardType;
              res.data[i].boardType = '리뷰';
              this.contents.push(res.data[i]);
              // console.log(this.contents, i)
            }
            if (res.data[i].boardType === '자유 게시판') {
              res.data[i].createdDate =
                res.data[i].createdDate.slice(2, 4) +
                '/' +
                res.data[i].createdDate.slice(5, 7) + 
                '/' +
                res.data[i].createdDate.slice(8, 10);
              res.data[i].modifiedDate = res.data[i].boardType;
              res.data[i].boardType = '자유';
              this.contents.push(res.data[i]);
              // console.log(this.contents, i)
            }
            if (res.data[i].boardType === '세일 제보 게시판') {
              res.data[i].createdDate =
                res.data[i].createdDate.slice(2, 4) +
                '/' +
                res.data[i].createdDate.slice(5, 7) + 
                '/' +
                res.data[i].createdDate.slice(8, 10);
              res.data[i].modifiedDate = res.data[i].boardType;
              res.data[i].boardType = '제보';
              this.contents.push(res.data[i]);
              // console.log(this.contents, i)
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    create: function() {
      this.$router.push({
        path: '/board/create',
      });
    },
    rowClick(contents) {
      this.$router.push({
        path: `/board/detail/${contents.boardNo}`,
      });
    },
    rowClickLoginMassage() {
      this.$router.push({
        path: '/login',
      });
      alert('로그인 후 이용해 주세요!');
    },
  },
};
</script>

<style>
.mdi-arrow-up::before {
  content: '';
}
.v-application--is-ltr .v-data-table > .v-data-table__wrapper > table > thead > tr > th {
  text-align: center;
}
#board .v-slide-group__content {
  justify-content: center;
  font-size: 2em;
}
element.style {
  padding-bottom: 0%;
  background-color: black;
}
.v-responsive__sizer {
  flex: 0px;
}
</style>
