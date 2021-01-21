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
                <h1 class="pt-2">새 글 작성하기 / 글 수정하기</h1>
              </v-card-title>
              <br>
              <v-card-text
              >분류
              </v-card-text>

              <v-col
                class="pl-4"
                cols="12"
                sm="2"
                >
                <v-select
                :items="category"
                label="글의 분류를 선택하세요"
                outlined
                ></v-select>
              </v-col>


              <v-card-text
              >제목
              </v-card-text>
              
              <v-text-field
                class="pl-4 pr-4"
                label="제목을 입력해 주십시오"
                :rules="titlerules"
                hide-details="auto"
                outlined
                
                ></v-text-field>

              <v-card-text
              >내용
              </v-card-text>

              <v-textarea
                class="pl-4 pr-4"
                label="내용을 입력해 주십시오"
                :rules="contentrules"
                hide-details="auto"
                outlined
                height=200
                ></v-textarea>

              <v-card-text
              >TAG
              </v-card-text>

              <v-text-field
                class="pl-4 pr-4"
                label="태그를 입력해 주십시오"
                outlined
                ></v-text-field>

              <v-card-text
              >파일첨부
              </v-card-text>

              <v-file-input
                truncate-length="15"
                class="pl-5 pr-5"
                ></v-file-input>

              <v-btn 
                class="mr-1 ml-4 mb-6"
                elevation="2"
                @click="create"
                color="grey lighten-2"
                >저장</v-btn>
              <v-btn 
                class="mb-6"
                elevation="2"
                @click="backtoboard"
                color="grey lighten-2"
                >목록</v-btn>

                

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
      titlerules: [
        value => (value && value.length >= 3) || '최소 3글자 이상 입력해 주세요',
        value => (value && value.length <= 25) || '25자 이상 입력할 수 없습니다',
      ],
      contentrules: [
        value => (value && value.length >= 3) || '최소 3글자 이상 입력해 주세요',
        value => (value && value.length <= 50) || '50자 이상 입력할 수 없습니다',
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
            alert("새 글 작성이 완료되었습니다.")
        }
    }
  }
</script>

<style>

</style>