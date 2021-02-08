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
                <h1>리뷰게시판</h1>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-text-field
                  
                  class="pr-2 pl-14"
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
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
                sort-by="boardNo"
                sort-desc="true"
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
      // 전체 게시판 글 가져오기
      axios
        .get("http://localhost:8080/api/boardList/")
        .then((res) => {
          console.log(res)
          this.contents = res.data
        })
        this.getList()
        .catch((err) => {
          console.log(err);
        });

    },
    data () {
      return {
        search: '',
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        headers: [
          {
            width: '100',
            text: '게시글 번호',
            align: 'center',
            sortable: false,
            value: 'boardNo',
          },
          // 내용에 sortable: false를 추가하면 제목을 클릭 시 sort 되는 것을 제거
          { text: '제목', value: 'boardTitle', align: 'start', sortable: false,},
          { width: '150', text: '작성자', value: 'usrEmail', align: 'start', sortable: false,},
          { width: '150', text: '등록일', value: 'createdDate', align: 'center', sortable: false,},
        ],
        contents: [
        
        ],
      }
    },
    methods: {
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