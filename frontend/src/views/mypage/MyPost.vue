<template>
  <v-container class="">
    <v-row no-gutters>

      <v-col
        cols="12"
        sm="1"
      >
      </v-col>

      <v-col
        cols="12"
        sm="10"
      >
        <v-card
          class="pa-2"
          outlined
          tile
        >
          <div>
            

            <v-card>
              <v-card-title>
                <h1 class="mt-2 mb-2">내 게시글</h1>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>

                <v-btn
                  color="grey lighten-4"
                  @click="mypage"
                >
                  <v-icon>mdi-account-circle</v-icon>
                </v-btn>


                <v-btn
                  class="mx-2"
                  color="grey lighten-4"
                  @click="board"
                >
                  <v-icon dark>
                    mdi-format-list-bulleted-square
                  </v-icon>
                </v-btn>

              </v-card-title>
              <v-data-table
                :headers="headers"
                :items="contents"
                :search="search"
                class="pl-4 pr-4 elevation-1"
                :page.sync="page"
                :items-per-page="itemsPerPage"
                hide-default-footer
                @page-count="pageCount = $event"
                multi-sort=true
                sort-by="['pk', 'id']"
                sort-desc="[true, false]"
              ></v-data-table>

              <div class="text-center pt-2">
              <v-pagination
                v-model="page"
                :length="pageCount"
              ></v-pagination>
              </div>
            </v-card>
          </div>
        </v-card>
      </v-col>

      <v-col
        cols="12"
        sm="1"
      >
      </v-col>

    </v-row>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        search: '1',
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        headers: [
          {
            width: '100',
            text: '게시글 번호',
            align: 'center',
            sortable: false,
            value: 'id',
            multisort: true,
            sortby:"['pk', 'id']",
            sortdesc:"[true, false]",
          },
          { text: '제목', value: 'title', align: 'start',},
          { width: '100', text: '작성자', value: 'username', align: 'start',},
          { width: '150', text: '작성 시간', value: 'created_at', align: 'center',},
          { width: '150', text: '수정 시간', value: 'updated_at', align: 'center', },
        ],
        contents: [
          {
            pk: '1',
            id: '1',
            username: '홍길동',
            title: '동해물과',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '2',
            username: '홍길동',
            title: '동해물과 백두산이',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '3',
            username: '홍길동',
            title: '동해물과 백두산이 마르고',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '4',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '5',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록 하느님이',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '6',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '7',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '8',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '2',
            id: '9',
            username: '홍길동2',
            title: 9.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '3',
            id: '10',
            username: '홍길동3',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '4',
            id: '11',
            username: '홍길동4',
            title: 3.7,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '5',
            id: '12',
            username: '홍길동5',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:57',
          },
          {
            pk: '6',
            id: '13',
            username: '홍길동6',
            title: 0.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '7',
            id: '14',
            username: '홍길동7',
            title: 0.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '8',
            id: '15',
            username: '홍길동8',
            title: 3.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '9',
            id: '16',
            username: '홍길동9',
            title: 25.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '10',
            id: '17',
            username: '홍길동10',
            title: 26.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '11',
            id: '18',
            username: '홍길동11',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '12',
            id: '19',
            username: '홍길동12',
            title: 9.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '13',
            id: '20',
            username: '홍길동13',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '14',
            id: '21',
            username: '홍길동14',
            title: 3.7,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '15',
            id: '22',
            username: '홍길동15',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:57',
          },
          {
            pk: '16',
            id: '23',
            username: '홍길동16',
            title: 0.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '17',
            id: '24',
            username: '홍길동17',
            title: 0.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '18',
            id: '25',
            username: '홍길동18',
            title: 3.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '19',
            id: '26',
            username: '홍길동19',
            title: 25.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            pk: '20',
            id: '27',
            username: '홍길동20',
            title: 26.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
        ],
      }
    },
    methods: {
      board: function () {
        this.$router.push({
          path: '/board'
        })
      },
      mypage: function () {
        this.$router.push({
          path: '/mypage'
        })
      },
    }
  }
</script>

<style>

</style>