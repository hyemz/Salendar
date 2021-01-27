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
                <h1 class="pt-2 pr-4">제목 1</h1>
                <h4 class="pr-4 pt-2">#태그 1</h4>
                <h4 class="pr-4 pt-2">#태그 2</h4>

                <v-spacer></v-spacer>
                <v-btn 
                  class="mr-1 ml-4"
                  elevation="2"
                  @click="create"
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
              <v-card-text
              >분류
              </v-card-text>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >분류분류분류분류분류분류</v-card>

              </div>

              <v-card-text
              >내용
              </v-card-text>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >내용내용내용내용내용내용내용내용내용내용내용내용내용내용</v-card>
              </div>

              <v-card-text
              >사진
              </v-card-text>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >사진사진사진사진사진사진사진사진사진</v-card>
              </div>

              <v-card-text
              >댓글 작성하기
              </v-card-text>

              <v-text-field
                class="pl-4 pr-4"
                label="댓글을 입력해 주십시오!!!!!!!!!!!"
                :rules="titlerules"
                hide-details="auto"
                outlined
                
                ></v-text-field>

              <v-card-text
              >댓글
              </v-card-text>

              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >댓글댓글댓글댓글댓글댓글댓글</v-card>
              </div>
              <div class="pl-4 pr-4">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >댓글댓글댓글댓글댓글댓글댓글</v-card>
              </div>
              <div class="pl-4 pr-4 pb-6">
                <v-card
                  class="pl-3 pr-3"
                  elevation="2"
                  outlined
                >댓글댓글댓글댓글댓글댓글댓글</v-card>
              </div>



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
    data: () => ({
      rules: [
        value => (value && value.length >= 3) || '최소 3글자 이상 입력해 주세요',
      ],
      category: ['자유게시판', '제품 리뷰', '세일 제보 게시판',],
    }),
    methods: {
        backtoboard () {
          this.$router.push({
          path: '/board'
          })
        },
        create () {
          this.$router.push({
          path: '/board/create'
          })
        }
    }
  }
</script>

<style>

</style>