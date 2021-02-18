import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                main: '#08376b',
                sub: '#00aeff',
            }
        }
    }
});
