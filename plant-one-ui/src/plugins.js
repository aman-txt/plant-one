import Vue from "vue"
import _ from "lodash"
import { default as GlobalHelpers } from "@/helpers/global"

const VuePlantOnePlugin = {
    install() {
        // global helpers
        Object.defineProperty(Vue, "helpers", { value: GlobalHelpers })
        Object.defineProperty(Vue.prototype, "$helpers", { value: GlobalHelpers })
        
        // lodash
        Object.defineProperty(Vue, "_", { value: _ })
        Object.defineProperty(Vue.prototype, "$_", { value: _ })
    }
}

export default VuePlantOnePlugin