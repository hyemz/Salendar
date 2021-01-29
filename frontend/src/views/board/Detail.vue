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
                <h1 class="pt-2 pr-4">[제품 리뷰] 피지오겔 AI 진정보습 로션</h1>
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
                  max-width="100"
                >제품 리뷰</v-card>

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
                >확실히 번들번들하고 보습력이 좋다는 걸 느꼈어요.
                제품 사용전 피부가 하얗게 일어나는 건성피부였으나
                사용 후 각질이 일어나는 증상은 매우 완화가 되었어요</v-card>
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
                ></v-text-field>

              <v-card-text
              >
              <h1>댓글</h1>
              </v-card-text>

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