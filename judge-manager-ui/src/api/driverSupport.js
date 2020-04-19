import { get } from '../util/http';

export function exists(key) {
  return get(`/driver-support/${key}/exists`, {});
}

export function inspect(key) {
  return get(`/driver-support/${key}`, {});
}

export function all(page, rows) {
  return get('/driver-support/all', {
    page,
    rows,
  });
}

export function idLike(pattern, page, rows) {
  return get('/driver-support/id-like', {
    pattern,
    page,
    rows,
  });
}

export function labelLike(pattern, page, rows) {
  return get('/driver-support/label-like', {
    pattern,
    page,
    rows,
  });
}
