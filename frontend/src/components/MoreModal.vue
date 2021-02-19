<template>
  <v-row justify="center">
    <v-dialog v-model="isDialog" scrollable max-width="900px" persistent>
      <v-card color="white">
        <v-card color="grey lighten-4">
          <v-toolbar flat color="grey lighten-4">
            <v-spacer></v-spacer
            ><v-card-actions>
              <v-btn color="grey" text @click="isDialog = false">
                <v-icon>mdi-window-close</v-icon>
              </v-btn>
            </v-card-actions>
          </v-toolbar>

          <template>
            <v-card class="mx-auto" max-width="780" outlined>
              <v-list-item three-line>
                <v-list-item-content>
                  <v-alert></v-alert>
                  <div class="overline mb-1 ">
                    <v-avatar tile class="ma-3 align-center" width="150">
                      <v-img :src="logoPath"></v-img>
                    </v-avatar>
                  </div>

                  <template>
                    <v-col class=" mt-7">
                      <!-- <v-list-item-avatar tile size="150" color="grey"></v-list-item-avatar> -->

                      <v-list-item-subtitle class="mb-2">
                        {{ this.name }}
                      </v-list-item-subtitle>
                      <v-list-item-title class="headline mb-2">
                        {{ this.data.saleTitle }}
                      </v-list-item-title>

                      <v-list-item-subtitle>
                        {{
                          $moment(this.focus.start.setDate(this.focus.start.getDate())).format(
                            'YYYY-MM-DD'
                          )
                        }}
                        ~
                        {{
                          $moment(this.focus.end.setDate(this.focus.end.getDate() - 1)).format(
                            'YYYY-MM-DD'
                          )
                        }}
                        <strong>{{ '(D' + dday + ')' }}</strong>
                      </v-list-item-subtitle>
                    </v-col>
                  </template>
                </v-list-item-content>
                <!-- <v-list-item-avatar tile size="150" color="grey">
                  <v-img :src="logoPath"></v-img
                ></v-list-item-avatar> -->
              </v-list-item>

              <v-card-actions id="go" class="d-flex flex-column align-right mt-3">
                <v-btn outlined rounded text :href="this.link" target="_blank">
                  세일 바로 가기
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12"></v-col>
              </v-row>
            </v-container>
            <!-- <small>*indicates required field</small> -->
          </v-card-text>

          <template>
            <v-card class="mx-auto" max-width="1000" outlined>
              <img id="bigImg" :src="this.data.bigImg" />
            </v-card>
          </template>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12"></v-col>
              </v-row>
            </v-container>
            <!-- <small>*indicates required field</small> -->
          </v-card-text>
        </v-card>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
export default {
  data() {
    return {
      name: '',
      logoPath: '',
      data: Object,
      dday: '',
      isDialog: false,
      link: '',
      path: {
        미샤: require('@/assets/Logo/Missha.png'),
        아리따움: require('@/assets/Logo/Aritaum.png'),
        에뛰드하우스: require('@/assets/Logo/Etude.png'),
        랄라블라: require('@/assets/Logo/Lalavla.png'),
        올리브영: require('@/assets/Logo/Oliveyoung.png'),
        토니모리: require('@/assets/Logo/Tonymoly.png'),
        더페이스샵: require('@/assets/Logo/Thefaceshop.png'),
        이니스프리: require('@/assets/Logo/Innisfree.png'),
      },
    };
  },
  created() {},
  props: {
    dialog: Boolean,
    focus: Object,
  },
  watch: {
    dialog: function() {
      this.data = this.focus._def.extendedProps;
      console.log(this.focus);
      const d1 = this.$moment(this.focus.end).format('YYYY-MM-DD');
      this.dday = this.$moment(d1).diff(this.$moment(), 'days');
      if (this.dday < 0) {
        this.dday = '+' + (Math.abs(this.dday) + 1);
      } else if (this.dday == 0) {
        this.dday = '-day';
      } else {
        this.dday = '-' + Math.abs(this.dday);
      }
      console.log(this.data);
      this.isDialog = this.dialog;
      this.name = this.data.storeName.substring(0, this.data.storeName.length - 3);
      this.link = this.data.saleLink;
      this.logoPath = this.path[this.name.replace(' ', '')];
      this.$emit('close', this.isDialog);
    },
    isDialog: function() {
      this.$emit('close', this.isDialog);
    },
  },
};
</script>
<style scoped>
#bigImg {
  width: 100% !important;
  margin-bottom: -8px;
  /* height: auto !important; */
}
.headline {
  font-family: 'NanumBarunGothic' !important;
  font-weight: 500;
}
@font-face {
  font-family: 'NanumBarunGothic';
  font-style: normal;
  font-weight: 1000;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot?#iefix')
      format('embedded-opentype'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.woff') format('woff'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.ttf') format('truetype');
}
#ll {
  position: absolute;
  align-self: 2;
  top: 85px;
  z-index: 2;
}
#go {
  align-items: flex-end;
  padding: 8px;
  padding-right: 40px;
  padding-bottom: 30px;
}
</style>
