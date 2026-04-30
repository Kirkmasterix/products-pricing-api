import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://app:8080';

export function setup() {
  // 🔥 espera a que la app esté lista UNA SOLA VEZ
  for (let i = 0; i < 40; i++) {
    const res = http.get(`${BASE_URL}/products`);
    if (res.status !== 0) {
      console.log("App lista");
      return;
    }
    sleep(1);
  }

  throw new Error("App no arrancó a tiempo");
}

export const options = {
  stages: [
    { duration: '10s', target: 20 },
    { duration: '20s', target: 50 },
    { duration: '10s', target: 0 },
  ],
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
  }
};

export default function () {

  const createRes = http.post(`${BASE_URL}/products`,
    JSON.stringify({
      name: `Product ${Math.random()}`,
      description: 'load test product',
      initialPrice: 100,
      startDate: '2024-01-01'
    }),
    { headers: { 'Content-Type': 'application/json' } }
  );

  check(createRes, {
    'product created': (r) => r.status === 200 || r.status === 201,
  });

  let productId;
  try {
    productId = createRes.json().id;
  } catch (e) {
    return;
  }

  const priceRes = http.post(`${BASE_URL}/prices`,
    JSON.stringify({
      value: 200,
      initDate: '2024-02-01',
      productId
    }),
    { headers: { 'Content-Type': 'application/json' } }
  );

  check(priceRes, {
    'price created': (r) => r.status === 200 || r.status === 201,
  });

  const getRes = http.get(`${BASE_URL}/prices/product/${productId}?date=2024-02-01`);

  check(getRes, {
    'price retrieved': (r) => r.status === 200,
  });

  sleep(1);
}