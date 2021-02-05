<template>
  <v-row justify="center">
    <v-dialog v-model="isDialog" scrollable max-width="900px" persistent>
      <v-card color="white">
        <v-card color="grey lighten-4">
          <v-toolbar flat color="grey lighten-4">
            <v-spacer></v-spacer
            ><v-card-actions>
              <v-btn color="grey" text @click="isDialog = false">
                <v-icon>mdi-close-box-outline</v-icon>
              </v-btn>
            </v-card-actions>
          </v-toolbar>

          <template>
            <v-card class="mx-auto" max-width="780" outlined>
              <v-list-item three-line>
                <v-list-item-content>
                  <div class="overline mb-4">
                    <v-avatar class="ma-3" width="150">
                      <v-img :src="logoPath"></v-img>
                    </v-avatar>
                  </div>

                  <template>
                    <v-list-item-subtitle>
                      {{ this.name }}
                    </v-list-item-subtitle>
                    <v-list-item-title class="headline mb-2">
                      {{ this.data.saleTitle }}
                    </v-list-item-title>

                    <v-list-item-subtitle>
                      {{ $moment(this.focus.start).format('YYYY-MM-DD') }} ~
                      {{ $moment(this.focus.end).format('YYYY-MM-DD') }}
                    </v-list-item-subtitle>
                  </template>
                </v-list-item-content>
              </v-list-item>

              <v-card-actions class="d-flex flex-column justify-center align-right mt-3">
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
  /* height: auto !important; */
}
</style>
