# AgeraCore

version name: 1.0, version code: 1

#### Sample

```java
fit = new AgeraFit.Builder()
	.setBaseUrl(API_URL)
	.setConverterFactory(GsonConverterFactory.create())
	.build();
```

#### Usage

To add a dependency using Gradle:

```groovy
compile 'AgeraCore:agera:0.0.1'
```

It supports `Repository<Result<T>>`,  
you could write your service interface like this:

```java
interface GitHub{
    @Get("/repos/google/agera/contributors")
    Repository<Result<List<Contributor>>> get();
	@Post("/repos/alibaba/AndFix/contributors")
	Repository<Result<List<Contributor>>> post();
	@Rest("/repos/{owner}/{repo}/contributors")
    Repository<Result<List<Contributor>>> rest(@Query("owner") String owner,@Query("repo") String repo);
}
```

Config your AgeraFit, like this:  

```java
AgeraFit fit = new AgeraFit.Builder()
	.setBaseUrl(API_URL)
	.setConverterFactory(GsonConverterFactory.create())
	.build();
	
GitHub git = fit.create(GitHub.class);
Repository<Result<List<Contributor>>> repository = git.contributors("square", "retrofit");
repository.addUpdatable(receiver);
```

Config your AgeraReceiver, like this:

```java
AgeraReceiver<List<Contributor>> receiver = new AgeraReceiver<List<Contributor>>() {
	@Override
	public void accept(@NonNull List<Contributor> contributors) {
       ...
	}

    @Override
	public void update() {
		repository.get().ifFailedSendTo(err).ifSucceededSendTo(receiver);
	}
};

```

#### Addition

- 此项目是用于学习Agera的使用、发布lib到jcenter等等

#### Link
- [Agera](https://github.com/google/agera/wiki)  
- [同事拒绝Retrofit，怎么办？](http://www.jianshu.com/p/9bafd93cea73)   
- [Android开发发布lib到jcenter](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0824/6566.html)


License
=======

    Copyright (C) 2016 peace.
       http://peace.me
       
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    


