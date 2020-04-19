import {
  del, get, patch, post,
} from '../util/http';

export function exists(key) {
  return get(`/judger-info/${key}/exists`, {});
}

export function inspect(key) {
  return get(`/judger-info/${key}`, {});
}

export function insert(key, sectionkey, enabled, remark, content, type) {
  let finalKey = { long_id: key };
  if (key === '') {
    finalKey = null;
  }
  return post('/judger-info', {
    key: finalKey,
    sectionKey: {
      long_id: sectionkey,
    },
    enabled,
    remark,
    content,
    type,
  });
}

export function remove(key) {
  return del(`/judger-info/${key}`, {});
}

export function update(key, sectionkey, enabled, remark, content, type) {
  return patch('/judger-info', {
    key: {
      long_id: key,
    },
    sectionKey: {
      long_id: sectionkey,
    },
    enabled,
    remark,
    content,
    type,
  });
}

export function all(page, rows) {
  return get('/judger-info/all', {
    page,
    rows,
  });
}

export function childForSection(key, page, rows) {
  return get(`/section/${key}/judger-infos`, {
    key,
    page,
    rows,
  });
}
