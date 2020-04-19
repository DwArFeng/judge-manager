import { get } from '../util/http';

export function exists(key) {
  return get(`/judger-support/${key}/exists`, {});
}

export function inspect(key) {
  return get(`/judger-support/${key}`, {});
}

export function all(page, rows) {
  return get('/judger-support/all', {
    page,
    rows,
  });
}

export function idLike(pattern, page, rows) {
  return get('/judger-support/id-like', {
    pattern,
    page,
    rows,
  });
}

export function labelLike(pattern, page, rows) {
  return get('/judger-support/label-like', {
    pattern,
    page,
    rows,
  });
}
