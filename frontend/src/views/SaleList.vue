<template>
  <div>
    <div>
      <v-card 
      color="primary darken-2"
      height="350"
      >

        <v-carousel
          :continuous="true"
          :cycle="cycle"
          hide-delimiter-background
          show-arrows-on-hover
          height="300"
          hide-delimiters
        >
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
          >

              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <div class="display-2">
                  <div>
                    {{ i+1 }} Slide
                  </div>
                  <div class="d-flex align-end justify-end">
                    {{ i+1 }} / {{ items.length }}
                  </div>
                </div>
              <p class="text-left text-xl-left">{{ i+1 }} / {{ items.length }}</p>
              </v-row>

          </v-carousel-item>
        </v-carousel>
        <v-list-item-action class="d-flex align-end justify-end">
          <div>
            <v-switch
              v-model="cycle"
              inset
              color="red"
            >
            </v-switch>
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
                v-model="model"
                class="pa-4"
                center-active
                show-arrows
              >
                <v-slide-item
                  v-for="n in 15"
                  :key="n"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="`https://picsum.photos/500/300?image=${n * 5 + 10}`"
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
                          v-text="'mdi-close-circle-outline'"
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
                v-model="model"
                class="pa-4"
                center-active
                show-arrows
              >
                <v-slide-item
                  v-for="n in 15"
                  :key="n"
                  v-slot="{ active, toggle }"
                >
                  <v-card
                    :img="`https://picsum.photos/500/300?image=${n * 5 + 9}`"
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
                          v-text="'mdi-close-circle-outline'"
                        ></v-icon>
                      </v-scale-transition>
                    </v-row>
                  </v-card>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>
        <v-row>
          <v-col cols="12" sm="6">
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

          </v-col>

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
            src: 'https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/sky.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/bird.jpg',
          },
          {
            src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg',
          },
        ],
        cycle: false,
        model: null,
      }
    },
  }
</script>

<style>

</style>