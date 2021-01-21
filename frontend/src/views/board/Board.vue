<template>
  <v-container class="">
    <v-row no-gutters>

      <v-col
        cols="12"
        sm="1"
      >
        <v-card
          class="pa-2"
          outlined
          tile
        >
          1st cloumn
        </v-card>
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
                <h1>자유게시판</h1>
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
                  color=""
                  @click="create"
                >
                  <v-icon left>
                    mdi-pencil
                  </v-icon>
                  New
                </v-btn>

              </v-card-title>
              <v-data-table
                class="pl-4 pr-4"
                :headers="headers"
                :items="contents"
                :search="search"
              ></v-data-table>
            </v-card>
          </div>
        </v-card>
      </v-col>

      <v-col
        cols="12"
        sm="1"
      >
        <v-card
          class="pa-2"
          outlined
          tile
        >
          3rd column
        </v-card>
      </v-col>

    </v-row>
  </v-container>
</template>

<script>
  export default {
    data () {
      return {
        search: '',
        headers: [
          {
            text: '게시글 번호',
            align: 'start',
            sortable: false,
            value: 'id',
          },
          { text: '작성자', value: 'username', align: 'start',},
          { text: '제목', value: 'title', align: 'start',},
          { text: '작성 시간', value: 'created_at', align: 'center',},
          { text: '수정 시간', value: 'updated_at', align: 'center', },
        ],
        contents: [
          {
            id: '1',
            username: '홍길동',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '2',
            username: '홍길동2',
            title: 9.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '3',
            username: '홍길동3',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '4',
            username: '홍길동4',
            title: 3.7,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '5',
            username: '홍길동5',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:57',
          },
          {
            id: '6',
            username: '홍길동6',
            title: 0.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '7',
            username: '홍길동7',
            title: 0.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '8',
            username: '홍길동8',
            title: 3.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '9',
            username: '홍길동9',
            title: 25.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '10',
            username: '홍길동10',
            title: 26.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '11',
            username: '홍길동11',
            title: '동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라',
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '12',
            username: '홍길동12',
            title: 9.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '13',
            username: '홍길동13',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '14',
            username: '홍길동14',
            title: 3.7,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '15',
            username: '홍길동15',
            title: 16.0,
            created_at: '12:34:56',
            updated_at: '12:34:57',
          },
          {
            id: '16',
            username: '홍길동16',
            title: 0.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '17',
            username: '홍길동17',
            title: 0.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '18',
            username: '홍길동18',
            title: 3.2,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '19',
            username: '홍길동19',
            title: 25.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
          {
            id: '20',
            username: '홍길동20',
            title: 26.0,
            created_at: '12:34:56',
            updated_at: '12:34:56',
          },
        ],
      }
    },
    methods: {
      create: function () {
        this.$router.push({
          path: '/board/create'
        })
      },
    }
  }
</script>

<style>

</style>