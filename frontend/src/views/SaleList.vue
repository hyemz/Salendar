<template>
  <div>
    <div>
      <MoreModal :dialog="dialog" :focus="focus" @close="close" />
      <v-card 
      color="grey lighten-5"
      height=""
      >
        <v-carousel
          v-model="model"
          :continuous="true"
          :cycle="cycle"
          :show-arrows="false"
          hide-delimiter-background
          height="450"
          max-width=""
          hide-delimiters
          justify=""
          class="d-flex justify-center align-start"
        >
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            max-height=""
            max-width=""
          >
              <v-row
                class="fill-height mr-4"
                align="end"
                justify="end"
              >

              <v-card 
                class="d-flex align-end justify-end" 
                color="transparent"
                elevation="0"
                >
                <v-btn
                  elevation="2"
                  color="grey lighten-2"
                  fab
                  x-small
                  @click="model--"
                >
                  <v-icon
                    color="white"
                  >
                    mdi-chevron-left
                  </v-icon>
                </v-btn>

                <div class="mr-2 ml-2" color="black">
                  {{ i+1 }} / {{ items.length }}
                </div>


                <v-btn
                  class="mr-2"
                  elevation="2"
                  color="grey lighten-2"
                  fab
                  x-small
                  @click="model++"
                >
                  <v-icon
                    color="white"
                  >
                    mdi-chevron-right
                  </v-icon>
                </v-btn>

                <v-btn
                  elevation="2"
                  color="grey lighten-2"
                  fab
                  x-small
                  @click="cycle=!cycle"
                >
                  <v-icon
                    color="blue"
                    v-if="!cycle"
                  >
                    mdi-play
                  </v-icon>
                  <v-icon
                    color="red"
                    v-else
                  >
                    mdi-pause
                  </v-icon>
                </v-btn>
              </v-card>

              </v-row>

          </v-carousel-item>
        </v-carousel>

        <v-row justify="center" align="right">
          <v-btn 
            @click="model--"
            elevation="2"
            color="grey lighten-2"
            fab
            x-small
          >
            <v-icon>
              mdi-chevron-left
            </v-icon>
          </v-btn>

          <v-btn
            color="transparent"
            elevation="0"
            aria-disabled="10"
          >
            {{ model + 1 }} / {{ items.length }}
          </v-btn>

          <v-btn
            class="mr-2"
            @click="model++"
            elevation="2"
            color="grey lighten-2"
            fab
            x-small
          >
            <v-icon>
              mdi-chevron-right
            </v-icon>
          </v-btn>

          <v-btn
            elevation="2"
            color="grey lighten-2"
            fab
            x-small
            @click="cycle=!cycle"
          >
            <v-icon
              color="blue"
              v-if="!cycle"
            >
              mdi-play
            </v-icon>
            <v-icon
              color="red"
              v-else
            >
              mdi-pause
            </v-icon>
          </v-btn>
        </v-row>

        <v-list-item-action class="d-flex align-end justify-end">
          <div>
            

          </div>

        </v-list-item-action>
      </v-card>
    </div>

    <v-container class="mt-10">
      <v-row no-gutters>

        <v-col cols="12" sm="1"></v-col>

        <v-col cols="12" sm="10">
          <div>
            <v-card
              max-width="130"
              outlined
              class="ml-16 h3"
            >
              <h3 class="text-center">
                진행 중인 세일
              </h3>
            </v-card>
            <v-sheet
              class="mx-auto"
              elevation="0"
              max-width=""
            >
              <v-slide-group
                v-model="model1"
                class="pa-4"
                center-active
                show-arrows
              >
                <v-slide-item
                  v-for="(nowSale, i) in nowSales"
                  :key="i"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="nowSale.sale_thumbnail"
                    :color="active ? 'primary' : 'grey lighten-3'"
                    class="ma-4"
                    height="200"
                    width="200"
                    @click="toggle, more(nowSale)"
                  >
                    <v-row
                      class="fill-height"
                      align="center"
                      justify="center"
                    >
                      <v-scale-transition>
                        <v-icon
                          v-if="active"
                          color="white"
                          size="48"
                          v-text="''"
                        ></v-icon>
                      </v-scale-transition>
                    </v-row>
                  </v-card>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>

          <div>
            <v-card
              max-width="130"
              outlined
              class="ml-16 h3"
            >
              <h3 class="text-center">
                진행 예정 세일
              </h3>
            </v-card>
            <v-sheet
              class="mx-auto"
              max-width=""
            >
              <v-slide-group
                v-model="model2"
                class="pa-4"
                center-active
                show-arrows
              >
                <v-slide-item
                  v-for="(preSale, i) in preSales"
                  :key="i"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="preSale.sale_thumbnail"
                    :color="active ? 'primary' : 'grey lighten-1'"
                    class="ma-4"
                    height="200"
                    width="200"
                    @click="toggle"
                  >
                    <v-row
                      class="fill-height"
                      align="center"
                      justify="center"
                    >
                      <v-scale-transition>
                        <v-icon
                          v-if="active"
                          color="white"
                          size="48"
                          v-text="''"
                        ></v-icon>
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
import axios from 'axios'
import moment from 'moment'
import MoreModal from '../components/MoreModal'

export default {
  components:{
    MoreModal,
  },
  data () {
    return {
      nowSales:[],
      preSales:[],
      stores:{
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
          src: require('@/assets/SaleList/oliveyoung_sale_1.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_2.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_3.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_4.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_5.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_6.png'),
        },
        {
          src: require('@/assets/SaleList/oliveyoung_sale_7.png'),
        },
      ],
      cycle: true,
      model: null,
      model1: null,
      model2: null,
      focus: null,
      dialog: false,
    }
  },
  created() {
    this.getSale()
  },
  methods: {
    slideGroup: 0,
    nextSlide() {
      this.slideGroup++;
      this.slideGroup == 12 ? (this.slideGroup = 0) : "";
    },
    previousSlide() {
      this.slideGroup--;
    },
    getSale(){
      // 세일정보 가져오기
      const baseURL = "http://localhost:8080";
      axios
      .create({
          baseURL,
      })
      .get('/api/sale/list')
      .then((res) => {
        this.sales = []
        const now = res.data
        
        const today = moment(new Date()).format('YYYY-MM-DD')
        
        Object.keys(now).forEach(store => {
          now[store].forEach(el => {
            el.store_name = this.stores[store].name
            if (el.sale_start_date <= today && today <= el.sale_end_date){
              this.nowSales.push(el)
            } else if (el.sale_start_date > today) {
              this.preSales.push(el)
            }
          })
        })
      })
      .catch((err) => {
        console.log("세일 로드에 실패했습니다.", err);
      });
    },
    more(sale){
      this.dialog = true
      this.focus = {
        start:sale.sale_start_date,
        end:sale.sale_end_date,
        _def:{
          extendedProps:{
            bigImg:sale.sale_big_img,
            saleDsc:sale.sale_dsc,
            saleLink:sale.sale_link,
            saleTitle:sale.sale_title,
            storeName:sale.store_name,
            thumbnail:sale.sale_thumbnail,
          }
        }
      }
    },
    close(isDialog) {
      this.dialog = isDialog;
    },
    }
  }
</script>

<style>
.theme--dark.v-image {
    color: black;
}
.v-application--is-ltr .v-responsive__sizer ~ .v-responsive__content {
    margin-left: 0%;
}
</style>