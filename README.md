# AgeraCore

version name: 1.0, version code: 1

#### Sample

```java
fit = new AgeraFit.Builder()
	.setBaseUrl("https://api.github.com")
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
	.setBaseUrl("https://api.github.com")
	.setConverterFactory(GsonConverterFactory.create())
	.build();
	
GitHub git = fit.create(GitHub.class);
Repository<Result<List<Contributor>>> repository = git.rest("square", "retrofit");
repository.addUpdatable(receiver);
```
It supports `AgeraReceiver<T>`,
you could write your receiver like this:

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
    


