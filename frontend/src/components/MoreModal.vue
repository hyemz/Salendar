<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" scrollable persistent max-width="700px">
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="grey" dark text v-bind="attrs" v-on="on" block elevation="0">
          로그인
        </v-btn>
      </template>

      <v-card>
        <v-toolbar flat>
          <v-card-title>
            <span class="headline" color>로그인</span>
          </v-card-title>
          <v-spacer></v-spacer
          ><v-card-actions>
            <v-btn color="grey" text @click="dialog = false" elevation="0">
              <font-awesome-layers full-width class="fa-2x">
                <font-awesome-icon :icon="['fas', 'times']" :style="{ color: 'lightgray' }" />
              </font-awesome-layers>
            </v-btn>
          </v-card-actions>
        </v-toolbar>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="아이디" required></v-text-field>
                <v-text-field label="비밀번호" type="password" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-card-actions>
                  <v-btn
                    color="happy"
                    @click="[login(), (dialog = false)]"
                    block
                    dark
                    elevation="2"
                    text-color="white"
                  >
                    로그인 </v-btn
                  ><br />
                </v-card-actions>
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
    focus: Date,
  },
};
</script>
