<template>
  <v-container 
    fluid
  >
    <v-img
      height="100px"
      src="@/assets/Board/board_title.jpg"
    >
    </v-img>
    <v-row no-gutters>

      <v-col cols="12" sm="2"></v-col>

      <v-col
        cols="12"
        sm="8"
      >
          <div>
              <v-card-title>
                <v-card
                  color="transparent"
                  outlined="false"
                  style="margin-top:-150px"
                >
                  <h1>세일 제보 게시판</h1>
                </v-card>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-text-field
                  sm="5"
                  class="pr-2 pl-14"
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
                  outlined
                  rounded
                ></v-text-field>

                <v-btn
                  tile
                  color="grey lighten-2"
                  @click="create"
                >
                  <v-icon left>
                    mdi-pencil
                  </v-icon>
                  New
                </v-btn>

              </v-card-title>
                <v-tabs>
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
              <v-data-table
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

              <div class="text-center pt-2">
              <v-pagination
                v-model="page"
                :length="pageCount"
              ></v-pagination>
              </div>
          </div>
      </v-col>

      <v-col cols="12" sm="1"></v-col>

    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'
  export default {
    created() {
      // 공지사항만 가져오기
      this.getnotifications()
      // 공지사항 제외 게시판 글 가져오기
      this.getboards()
    },
    data () {
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
          { width: '60', text: '분류', value: 'boardType', align: 'start', sortable: false,},
          { text: '제목', value: 'boardTitle', align: 'start', sortable: false,},
          { width: '150', text: '작성자', value: 'usrEmail', align: 'start', sortable: false,},
          { width: '120', text: '등록일', value: 'createdDate', align: 'center', sortable: false,},
          { width: '90', text: '조회수', value: 'hit', align: 'center', sortable: false,},
        ],
        contents: [
        
        ],
      }
    },
    methods: {
      getToday () {
        var date = new Date();
        var year = date.getFullYear();
        var month = ("0" + (1 + date.getMonth())).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);

        return year + "-" + month + "-" + day;
      },
      getnotifications () {
        axios
          .get("http://localhost:8080/api/boardList/")
          .then((res) => {
            // console.log(res.data[1].boardType)
            for (var i = 0; i < res.data.length; i++) {
              if (res.data[i].boardType === "공지사항") {
                res.data[i].createdDate = res.data[i].createdDate.slice(2, 4) + "/" + res.data[i].createdDate.slice(5, 7) + "/" + res.data[i].createdDate.slice(8, 10)
                res.data[i].modifiedDate = res.data[i].boardType
                res.data[i].boardType = "공지"
                this.contents.push(res.data[i])
                // console.log(this.contents, i)
              }
            }
            // console.log(this.contents)
          })
          .catch((err) => {
            console.log(err);
          });
      },
      getboards () {
        axios
          .get("http://localhost:8080/api/boardList/")
          .then((res) => {
            var count = 0
            for (var i = 0; i < res.data.length; i++) {
              if (res.data[i].boardType === "세일 제보 게시판") {
                if (res.data[i].createdDate.slice(undefined, 10) == this.getToday()) {
                  res.data[i].createdDate = res.data[i].createdDate.slice(10)
                } else {
                  res.data[i].createdDate = res.data[i].createdDate.slice(2, 4) + "/" + res.data[i].createdDate.slice(5, 7) + "/" + res.data[i].createdDate.slice(8, 10)
                }
                res.data[i].boardType = "제보"
                count += 1
                res.data[i].modifiedDate = count

                this.contents.push(res.data[i])
                console.log(this.contents)
              }
            }
          })
          .catch((err) => {
            console.log(err);
          });
      },
      create: function () {
        this.$router.push({
          path: '/board/create'
        })
      },
      rowClick (contents) {
        this.$router.push({
          path: `/board/detail/${contents.boardNo}`
        })
      },
    }
  }
</script>

<style>
.mdi-arrow-up::before {
    content: "";
}
.v-application--is-ltr .v-data-table > .v-data-table__wrapper > table > thead > tr > th {
  text-align: center;
}
</style>