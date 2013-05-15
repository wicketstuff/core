/* Copyright (c) 2013 Martin Knopf
 * 
 * Licensed under the MIT license;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
$ = {
  extend: function(defaultObject, newObject) {
    return defaultObject;
  }
};

exports.should_replace_fragment = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();

  window = {
      location: {
        hash: '#previousState',
        search: ''
      }
  };
  
  UrlUtil.setFragment('newState');
  
  test.equal(window.location.hash, '#!newState');
  test.done();
};

exports.should_replace_fragment_with_key_value_pair = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();

  window = {
      location: {
        hash: '#previousState',
        search: ''
      }
  };
  
  UrlUtil.setFragment('key', 'value');
  
  test.equal(window.location.hash, '#!key=value');
  test.done();
};

exports.readParameters_should_be_initialized_false = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  test.ok(!UrlUtil.readParameters, "this assertion should fail");
  test.done();
};

exports.should_create_parameter_map_from_location = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  test.deepEqual(UrlUtil.joinQueryAndFragment(), {hashParam: '1', queryParam: '2'});
  test.done();
};

exports.should_prefix_first_fragment_parameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('newParam', '666');
  
  test.equal(window.location.hash, '#!newParam=666');
  test.done();
};

exports.should_concat_fragment_parameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('newParam', '666');
  
  test.equal(window.location.hash, '#!hashParam=1&newParam=666');
  test.done();
};

exports.should_change_fragment_parameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('hashParam', '666');
  
  test.equal(window.location.hash, '#!hashParam=666');
  test.done();
};

exports.should_remain_fragment_parameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1&newParam=666',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.removeFragmentParameter('hashParam');
  
  test.equal(window.location.hash, '#!newParam=666');
  test.done();
};

exports.should_remove_prefix_when_last_fragment_parameter_removed = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.removeFragmentParameter('hashParam');
  
  test.equal(window.location.hash, '');
  test.done();
};

exports.should_set_editedFragmentParameter_true_after_putFragmentParameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('hashParam', '666');
  
  test.ok(UrlUtil.editedFragment);
  test.done();
};

exports.should_set_editedFragmentParameter_true_after_removeFragmentParameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.removeFragmentParameter('hashParam');
  
  test.ok(UrlUtil.editedFragment);
  test.done();
};

exports.should_add_fragment_parameter = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('hashParam', '666', '|');

  test.equal(window.location.hash, '#!hashParam=1|666');
  test.done();
};

exports.adding_should_create_fragment_parameter_if_doesnt_exit_yet = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!hashParam=1',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('newParam', '666', '|');

  test.equal(window.location.hash, '#!hashParam=1&newParam=666');
  test.done();
};

exports.adding_should_create_fragment_parameter_if_none_exits_yet = function(test) {
  var UrlUtil = require('./urlfragment.js').UrlUtil();
  
  window = {
      location: {
        hash: '#!',
        search: '?queryParam=2'
      }
  };
  
  UrlUtil.putFragmentParameter('newParam', '666', '|');

  test.equal(window.location.hash, '#!newParam=666');
  test.done();
};