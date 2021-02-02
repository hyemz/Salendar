<template>
  <div>
    <div>
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
                  v-for="(small2image, i) in small2images"
                  :key="i"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="small2image.src"
                    :color="active ? 'primary' : 'grey lighten-3'"
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
                  v-for="(small1image, i) in small1images"
                  :key="i"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="small1image.src"
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
export default {
    data () {
      return {
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
        small1images: [
          {
            src: require('@/assets/SaleListSmall/sale1_1.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_2.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_3.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_4.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_5.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_6.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_7.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_8.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_9.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_10.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_11.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_12.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale1_13.png'),
          },
        ],
        small2images: [
          {
            src: require('@/assets/SaleListSmall/sale2_1.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_2.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_3.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_4.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_5.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_6.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_7.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_8.png'),
          },
          {
            src: require('@/assets/SaleListSmall/sale2_9.png'),
          },
        ],
        cycle: true,
        model: null,
        model1: null,
        model2: null,
      }
    },
    methods: {
      slideGroup: 0,
      nextSlide() {
        this.slideGroup++;
        this.slideGroup == 12 ? (this.slideGroup = 0) : "";
      },
      previousSlide() {
        this.slideGroup--;
      }
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