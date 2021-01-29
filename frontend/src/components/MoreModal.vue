<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" scrollable persistent max-width="700px">
      <v-card>
        <v-toolbar flat>
          <v-card-title>
          </v-card-title>
          <v-spacer></v-spacer
          ><v-card-actions>
            <v-btn color="grey" text @click="dialog = false" elevation="0">
              x
              <!-- <font-awesome-layers full-width class="fa-2x">
                <font-awesome-icon :icon="['fas', 'times']" :style="{ color: 'lightgray' }" />
              </font-awesome-layers> -->
            </v-btn>
          </v-card-actions>
        </v-toolbar>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <h2>매장명 : {{ this.focus.title }}</h2>
                <h2>세일시작 : {{ this.focus.start }}</h2>
                <h2>세일끝 : {{ this.focus.end }}</h2>
              </v-col>
              <v-col cols="12">
                
              </v-col>
            </v-row>
          </v-container>
          <!-- <small>*indicates required field</small> -->
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
export default {
  data() {
    return {};
  },
  props: {
    dialog: Boolean,
    focus: Object,
  },
  watch: {
    dialog: function () {
      this.$emit('close', this.dialog)
    }

  }
};
</script>
