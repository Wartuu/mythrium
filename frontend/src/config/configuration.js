class ConfigValue {
    constructor(name, isTemp) {
        this._value = null;
        this.isTemp = isTemp;
        this.name = name;

        if (isTemp) {
            this._value = sessionStorage.getItem(name);
        } else {
            this._value = localStorage.getItem(name);
        }
    }

    get value() {
        return this._value;
    }

    set value(value) {
        this._value = value;

        if(value === undefined) {
            if(this.isTemp) sessionStorage.removeItem(this.name);
            else localStorage.removeItem(this.name)
            return;
        }

        if (this.isTemp) {
            sessionStorage.setItem(this.name, value);
        } else {
            localStorage.setItem(this.name, value);
        }
    }
}

class StorageManager {
    constructor() {
        if (!StorageManager.instance) {
            this.values = [];
            this.values.push(new ConfigValue('session', true));
            StorageManager.instance = this;
        }

        return StorageManager.instance;
    }

    getValue(name, isTemp = false) {
        const configValue = this.values.find(item => item.name === name && item.isTemp === isTemp);

        if (configValue instanceof ConfigValue) {
            return configValue;
        } else {
            const newConfigValue = new ConfigValue(name, isTemp);
            this.values.push(newConfigValue);
            return newConfigValue;
        }
    }
    
    

    setValue(name, value, isTemp = false) {
        const configValue = this.getValue(name, isTemp);
    
        if (configValue) {
            configValue.value = value;
        } else {
            const newConfigValue = new ConfigValue(name, isTemp);
            newConfigValue.value = value;
            this.values.push(newConfigValue);
        }
    }
    
}

const storageManager = new StorageManager();
Object.freeze(storageManager);

export default storageManager;