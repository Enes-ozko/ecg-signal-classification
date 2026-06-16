import tensorflow as tf
import numpy as np
import joblib

scaler = joblib.load("models/scaler.pkl")

def predict_signal(model_type, signal_data):
    model = tf.keras.models.load_model(f"models/best-model-{model_type}.keras")
    signal = np.array(signal_data).astype(np.float32).reshape(1, -1)
    signal_norm = scaler.transform(signal).astype(np.float32)

    if model_type == "mlp":
        input_data = signal_norm.reshape(1, 96)
    else:
        input_data = signal_norm.reshape(1, 96, 1)

    prediction = model.predict(input_data, verbose=0)
    prob = float(prediction[0][0])
    label = "Normal" if prob > 0.75 else "Infarctus"
    return label, prob