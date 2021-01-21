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
import axios from 'axios';

// 수정 필요 (백엔드와 연결방식에 따라)
const SERVER_URL = ''

export default {
  name: 'Board',
  created() {
    const config = this.setToken()
    // 수정 필요 (백엔드와 연결방식에 따라)
    axios.get(`${SERVER_URL}/board/`, config)
    .then((res) => {
      this.items = res.data
    })
    this.getList();
  },
  filters: {
    slice: function (origin) {
      return origin.slice(0, 19)
    }
  },
  data() {
    return {
      Page: 1,
      perPage: 10,
      fields: [
        {
          key: "id",
          label: "글번호",
        },
        {
          key: "title",
          label: "제목",
        },
        {
          key: "created_at",
          label: "등록일",
        },
        {
          key: 'user.username',
          label: '작성자',
        },
      ],
      items: []
    };
  },
  computed: {
    rows() {
      return this.items.length;
    }
  },
  methods: {
    // 수정 필요 (백엔드와 연결방식에 따라)
    // setToken: function () {
    //   const token = localstorage.getItem('jwt')

    //   const config = {
    //     headers: {
    //       Authorization: `JWT ${token}`
    //     }
    //   }
    //   return config
    // },
    // 경로 수정
    rowClick(item) {
      this.$router.push({
        path: `/board/free/detail/${item.id}`
      })
    },
    // 경로 수정
    writeContent() {
      this.$router.push({
        path: '/board/create'
      })
    },
  }
}
</script>

<style>

</style>