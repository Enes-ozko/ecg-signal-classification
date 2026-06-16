from flask import Flask, request, jsonify
import inference

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    model_type = data.get('type', 'cnn')
    signal = data.get('signal')
    
    label, score = inference.predict_signal(model_type, signal)
    
    return jsonify({
        "prediction": label,
        "probability": score,
        "model_used": model_type
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)