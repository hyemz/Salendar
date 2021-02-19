<template>
  <v-container class="mt-12">
    <v-row no-gutters>
      <v-col cols="12" sm="1"></v-col>
      <v-col cols="12" sm="10">
        <div class="mb-12">
          <v-card flat class="mt-12">
            <div class="titlefont"><strong>'S A L E N D A R'</strong> 를 만든 사람들</div>
          </v-card>
          <v-divider class="mt-12 mb-9"></v-divider>
        </div>
        <v-timeline class="mb-12">
          <v-timeline-item v-for="team in teams" :key="team.name" large>
            <template v-slot:icon>
              <v-avatar>
                <img :src="team.img" />
              </v-avatar>
            </template>
            <template v-slot:opposite>
              <span></span>
            </template>
            <v-card class="elevation-2">
              <v-card-title class="headline">
                <strong style="color:#08376b"> {{ team.name }} </strong>
                <v-col>
                  <v-badge :color="team.color" :content="team.position"></v-badge>
                </v-col>
                <!-- <v-col>
                  <v-badge class="align-end" :content="team.position"></v-badge>
                </v-col> -->
              </v-card-title>
              <v-card-text>{{ team.content }}</v-card-text>
            </v-card>
          </v-timeline-item>
        </v-timeline>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      teams: [
        {
          name: '고영길 ✨',
          position: 'Backend',
          img: require('@/assets/Team/gil.png'),
          content: '부엉',
          color: '#FF8F00',
        },
        {
          name: '김지영 🎨',
          position: 'Backend',
          img: require('@/assets/Team/young.png'),
          content: '#Backend #Spring #Java #크롤링 #배포 #컴공인데웹프로젝트는처음 #ISTJ #설렌다',
          color: '#FF8F00',
        },
        {
          name: '윤혜민 ⚽',
          position: 'Frontend',
          img: require('@/assets/Team/min.png'),
          content:
            '안녕하세요. frontend 막내 윤혜민입니다. 설레는 세일 캘린더 잘 사용해보셨나요~!! SALENDAR는 크롤링으로 실데이터를 가져와 세일 정보를 한 눈에 캘린더 형태로 표현한 유용한 웹 서비스입니다! 다들 재밌게 이용해보시면 좋을 것 같아요 🎈',
          color: '#00ACC1',
        },
        {
          name: '최정휴 👑',
          position: 'Frontend',
          img: require('@/assets/Team/hue.png'),
          content: '다들 제 목소리가 그리우신가요? 저도 여러분이 그립습니다.',
          color: '#00ACC1',
        },
        {
          name: '현성섭 🏔',
          position: 'Frontend',
          img: require('@/assets/Team/hyun.png'),
          content: '거 코딩하기 딱 좋은 날씨네',
          color: '#00ACC1',
        },
      ],
    };
  },
};
</script>
<style scoped>
.headline {
  font-family: 'S-CoreDream-3Light' !important;
}
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>
