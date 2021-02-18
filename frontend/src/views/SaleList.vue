<template>
  <div>
    <div>
      <MoreModal :dialog="dialog" :focus="focus" @close="close" />
      <v-card color="grey lighten-5" flat class="mb-12">
        <v-carousel
          v-model="model"
          :continuous="true"
          :cycle="cycle"
          :show-arrows="false"
          hide-delimiter-background
          height="450"
          hide-delimiters
          class="d-flex justify-center align-start"
        >
          <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src"> </v-carousel-item>
        </v-carousel>

        <!-- 카로셀 컨트롤러 -->
        <v-row justify="center" id="carouselControl">
          <v-btn color="white" dark rounded aria-disabled="10" id="su">
            <v-btn @click="model--" color="grey darken-3" text x-small>
              <v-icon>
                mdi-chevron-left
              </v-icon>
            </v-btn>
            {{ model + 1 }} / {{ items.length }}
            <v-btn @click="model++" color="grey darken-3" text x-small>
              <v-icon>
                mdi-chevron-right
              </v-icon>
            </v-btn>
          </v-btn>
          <v-btn color="white" dark rounded text aria-disabled="10" id="su">
            <v-btn color="white" text x-small @click="cycle = !cycle">
              <v-icon color="sub" v-if="!cycle">
                mdi-play
              </v-icon>
              <v-icon color="deep-orange" v-else>
                mdi-pause
              </v-icon>
            </v-btn>
          </v-btn>
        </v-row>
      </v-card>
    </div>

    <v-container class="mt-12">
      <v-row no-gutters>
        <v-col cols="12" sm="1"></v-col>
        <v-col cols="12" sm="10">
          <div>
            <v-card flat class="ml-16 h3 mt-12">
              <div class="text-center titlefont">설레는 <strong>세일</strong>을 알려드려요</div>
            </v-card>
            <v-divider class="mt-12 mb-12"></v-divider>
            <v-card flat class="ml-16 h3 mt-12">
              <div class="text-left subtitlefont"><strong>지금</strong> 세일 중~ 😘</div>
            </v-card>
            <v-sheet class="mx-auto">
              <v-slide-group v-model="model1" class="pa-4 mb-12" center-active show-arrows>
                <v-slide-item v-for="(nowSale, i) in nowSales" :key="i" v-slot="{ active, toggle }">
                  <v-card
                    :img="nowSale.sale_thumbnail"
                    :color="active ? 'primary' : 'grey lighten-3'"
                    class="ma-4"
                    height="200"
                    width="400"
                    @click="more(nowSale), toggle"
                  >
                    <v-row class="fill-height" align="center" justify="center">
                      <v-scale-transition>
                        <v-icon v-if="active" color="white" size="48" v-text="''"></v-icon>
                      </v-scale-transition>
                    </v-row>
                  </v-card>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>

          <div class="mb-12">
            <v-card flat class="ml-16 h3 mt-12">
              <div class="text-left subtitlefont">
                <strong>마감</strong>이 얼마 남지 않았네요 😥
              </div>
            </v-card>
            <v-sheet class="mx-auto">
              <v-slide-group v-model="model2" class="pa-4" center-active show-arrows>
                <v-slide-item v-for="(preSale, i) in preSales" :key="i" v-slot="{ active, toggle }">
                  <v-card
                    :img="preSale.sale_thumbnail"
                    :color="active ? 'primary' : 'grey lighten-1'"
                    class="ma-4"
                    height="200"
                    width="400"
                    @click="more(preSale), toggle"
                  >
                    <v-row class="fill-height" align="center" justify="center">
                      <v-scale-transition>
                        <v-icon v-if="active" color="white" size="48" v-text="''"></v-icon>
                      </v-scale-transition>
                    </v-row>
                  </v-card>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>
          <v-row>
            <!-- <v-col cols="12" sm="6">
            <v-card outlined class="mb-6 mt-6">
              <h3 class="ml-2 mt-1 mb-1">공지사항</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">공지사항 1</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">공지사항 2</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">공지사항 3</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">공지사항 4</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">공지사항 5</h3>
            </v-card>

          </v-col>
          <v-col cols="12" sm="6">
            <v-card outlined class="mb-6 mt-6">
              <h3 class="ml-2 mt-1 mb-1">인기 게시글</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">인기 게시글 1</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">인기 게시글 2</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">인기 게시글 3</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">인기 게시글 4</h3>
            </v-card>
            <v-card outlined>
              <h3 class="ml-2 mt-1 mb-1">인기 게시글 5</h3>
            </v-card>

          </v-col> -->
          </v-row>
        </v-col>

        <v-col cols="12" sm="1"></v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
import MoreModal from '../components/MoreModal';

export default {
  components: {
    MoreModal,
  },
  data() {
    return {
      nowSales: [],
      preSales: [],
      stores: {
        Oliveyoung: {
          name: '올리브영 🥑',
          color: '#BDEDD1',
        },
        Lalavla: {
          name: '랄라블라 💗',
          color: '#FFCFDC',
        },

        Innisfree: {
          name: '이니스프리 🌿',
          color: '#f7f8fb',
        },
        Thefaceshop: {
          name: '더 페이스샵 👩',
          color: '#DFC6FF',
        },
        Etude: {
          name: '에뛰드 하우스 👑',
          color: '#CFE4FF',
        },
        Missha: {
          name: '미샤 💋',
          color: '#FBEC8F',
        },
        Aritaum: {
          name: '아리따움 🎀',
          color: '#FFDABD',
        },
        Tonymoly: {
          name: '토니모리 🐼',
          color: '#CCD1D1',
        },
      },
      items: [
        {
          src: require('@/assets/SaleList/00.png'),
        },
        {
          src: require('@/assets/SaleList/01.png'),
        },
        {
          src: require('@/assets/SaleList/02.png'),
        },
        {
          src: require('@/assets/SaleList/03.png'),
        },
        {
          src: require('@/assets/SaleList/04.png'),
        },
      ],
      cycle: true,
      model: null,
      model1: null,
      model2: null,
      focus: null,
      dialog: false,
    };
  },
  created() {
    this.getSale();
  },
  methods: {
    slideGroup: 0,
    nextSlide() {
      this.slideGroup++;
      this.slideGroup == 12 ? (this.slideGroup = 0) : '';
    },
    previousSlide() {
      this.slideGroup--;
    },
    getSale() {
      // 세일정보 가져오기
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
        })
        .get('/api/sale/list')
        .then((res) => {
          this.sales = [];
          const now = res.data;
          const today = moment(new Date()).format('YYYY-MM-DD');
          Object.keys(now).forEach((store) => {
            now[store].forEach((el) => {
              el.store_name = this.stores[store].name;
              if (el.sale_start_date <= today && today <= el.sale_end_date) {
                this.nowSales.push(el);
              }
              if (
                0 < this.$moment(el.sale_end_date).diff(today, 'days') &&
                this.$moment(el.sale_end_date).diff(today, 'days') <= 3
              ) {
                console.log(this.$moment(el.sale_end_date).diff(today, 'days'));
                this.preSales.push(el);
              }
            });
          });
          if (this.preSales.length < 4) {
            this.preSales.push(
              {
                sale_thumbnail: require('@/assets/SaleListSmall/nosale.png'),
              },
              {
                sale_thumbnail: require('@/assets/SaleListSmall/nosale.png'),
              },
              {
                sale_thumbnail: require('@/assets/SaleListSmall/nosale.png'),
              },
              {
                sale_thumbnail: require('@/assets/SaleListSmall/nosale.png'),
              }
            );
          }
        })
        .catch((err) => {
          console.log('세일 로드에 실패했습니다.', err);
        });
    },
    more(sale) {
      this.dialog = true;
      let start = new Date(sale.sale_start_date);
      let end = new Date(sale.sale_end_date);
      this.focus = {
        start: new Date(start.setDate(start.getDate() + 1)),
        end: new Date(end.setDate(end.getDate() + 2)),
        _def: {
          extendedProps: {
            bigImg: sale.sale_big_img,
            saleDsc: sale.sale_dsc,
            saleLink: sale.sale_link,
            saleTitle: sale.sale_title,
            storeName: sale.store_name,
            thumbnail: sale.sale_thumbnail,
          },
        },
      };
      console.log('focus, start', this.focus, start);
    },
    close(isDialog) {
      this.dialog = isDialog;
    },
  },
};
</script>

<style>
.theme--dark.v-image {
  color: black;
}
.v-application--is-ltr .v-responsive__sizer ~ .v-responsive__content {
  margin-left: 0%;
}
.v-btn:not(.v-btn--round).v-size--x-small {
  height: 20px;
  min-width: 36px;
  padding: 0 0px;
}
.subtitlefont {
  font-family: 'S-CoreDream-3Light';
  font-size: 2em;
  font-weight: 500;
}
.titlefont {
  font-family: 'S-CoreDream-3Light';
  font-size: 3em;
}
.v-application .mb-12 {
  margin-bottom: 100px !important;
}
#su {
  top: -12px;
  right: -55px;
  font-family: 'Y_Spotlight';
  /* font-weight: bold; */
  color: #424242;
  opacity: 0.4;
  margin-bottom: 10px;
  padding: 0 0px !important;
}
#carouselControl {
  margin-top: -40px;
}
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'Y_Spotlight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/Y_Spotlight.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>
