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
                sort-by="id"
                sort-desc="true"
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
            username: '내일은해뜬다',
            title: '[제품 리뷰] 박준뷰티랩 LPP 단백질 헤어팩 트리트먼트',
            created_at: '21/01/27',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '2',
            username: '나노봇',
            title: '[제품 리뷰] 닥터지 더모이스처 배리어 D 인텐스 크림',
            created_at: '21/01/27',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '3',
            username: '페러라이드',
            title: '[제품 리뷰] 센카 퍼펙트 휩 페이셜 워시',
            created_at: '21/01/27',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '4',
            username: '네츄려얼',
            title: '[제품 리뷰] 리얼베리어 익스트림 크림 50ml',
            created_at: '21/01/27',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '5',
            username: '툴러',
            title: '[제품 리뷰] 메이크프렘 세이프미 릴리프 모이스처 클렌징폼',
            created_at: '21/01/27',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '6',
            username: '쩡쏭',
            title: '[제품 리뷰] 비비랩 저분자 콜라겐',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '7',
            username: '추적60인분',
            title: '[제품 리뷰] 라로슈포제 히알루B5 세럼',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '1',
            id: '8',
            username: '코카콜라',
            title: '[제품 리뷰] 티르티르 호호바 오일 기획세트 (100ml +10ml)',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '2',
            id: '9',
            username: '매운맛동산삼',
            title: '[제품 리뷰] 아이소이 1등잡티세럼 대용량 40ml',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '3',
            id: '10',
            username: '우크우크',
            title: '[제품 리뷰] 그라펜 핸섬 커버스틱 2,3호',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '4',
            id: '11',
            username: '오리의꿈',
            title: '[제품 리뷰] VT 슈퍼 히알론 올인원 에센스',
            created_at: '21/01/26',
            updated_at: '12:34:56',
          },
          {
            pk: '5',
            id: '12',
            username: '화난곰',
            title: '[제품 리뷰] 다슈 맨즈 빅 커버 비비',
            created_at: '21/01/26',
            updated_at: '12:34:57',
          },
          {
            pk: '6',
            id: '13',
            username: '벚꽃빛하늘',
            title: '[제품 리뷰] 달팡 하이드라스킨 리치 크림',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '7',
            id: '14',
            username: '파란내복',
            title: '[제품 리뷰] 낫포유 클리어 바디미스트',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '8',
            id: '15',
            username: '감자포테이토',
            title: '[제품 리뷰] 식물나라 산소수생기수분톤업선크림',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '9',
            id: '16',
            username: '슈베르트',
            title: '[제품 리뷰] 한스킨 블레미쉬커버 컨실러',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '10',
            id: '17',
            username: '추천릴레이',
            title: '[제품 리뷰] 라운드어라운드 드라이플라워 디퓨저',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '11',
            id: '18',
            username: '오렌지튀김',
            title: '[제품 리뷰] 다슈 맨즈 빅 커버 비비',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '12',
            id: '19',
            username: '도둑고양2',
            title: '[제품 리뷰] 라로슈포제 히알루B5 세럼 기획',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '13',
            id: '20',
            username: '나도계획이있었다',
            title: '[제품 리뷰] 힐링버드 울트라 프로틴 헤어오일리치',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '14',
            id: '21',
            username: '빵굽',
            title: '[제품 리뷰] 유기농본 울트라날개 오버나이트',
            created_at: '21/01/25',
            updated_at: '12:34:56',
          },
          {
            pk: '15',
            id: '22',
            username: '이또르',
            title: '[제품 리뷰] 바이너랩 겟 스키니',
            created_at: '21/01/24',
            updated_at: '12:34:57',
          },
          {
            pk: '16',
            id: '23',
            username: '키위왕',
            title: '[제품 리뷰] 피지오겔 DMT 마일드 립밤',
            created_at: '21/01/24',
            updated_at: '12:34:56',
          },
          {
            pk: '17',
            id: '24',
            username: '시어도어',
            title: '[제품 리뷰] 닥터지 더모이스처 배리어 D 인텐스 크림',
            created_at: '21/01/24',
            updated_at: '12:34:56',
          },
          {
            pk: '18',
            id: '25',
            username: '엑소레이',
            title: '[제품 리뷰] 한스킨 블랙헤드 멜팅 클렌징 젤 폼',
            created_at: '21/01/24',
            updated_at: '12:34:56',
          },
          {
            pk: '19',
            id: '26',
            username: '나노봇',
            title: '[제품 리뷰] 에스쁘아 꾸뛰르 립틴트 샤인',
            created_at: '21/01/24',
            updated_at: '12:34:56',
          },
          {
            pk: '20',
            id: '27',
            username: '공작부인',
            title: '[제품 리뷰] 피지오겔 AI 진정보습 로션',
            created_at: '21/01/24',
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